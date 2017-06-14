package com.alexsukharev.hackernewsreader.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.alexsukharev.hackernewsreader.database.AppDatabase;
import com.alexsukharev.hackernewsreader.model.Item;
import com.alexsukharev.hackernewsreader.network.HackerNewsApi;

import java.util.List;

public class ItemsRepository {

    private final HackerNewsApi mApi;

    private final AppDatabase mDatabase;

    public ItemsRepository(@NonNull final AppDatabase database, @NonNull final HackerNewsApi api) {
        mDatabase = database;
        mApi = api;
    }

    public LiveData<List<Item>> getStories(final boolean refreshFromNetwork) {
        if (refreshFromNetwork) {
            // Get IDs of top stories from network.
            mApi.getTopStories()
                    // Retry up to 3 times if the call fails
                    .retry(3)
                    // Iterate over the list of blank items
                    .flatMapIterable(ids -> ids)
                    // Fetch details of each item from network
                    .flatMap(mApi::getItem)
                    // Retry up to 3 times if the call fails
                    .retry(3)
                    .filter(item -> item != null)
                    // Collect items
                    .toList()
                    // Insert the list of items to the database
                    .subscribe(items -> mDatabase.itemDao().insertItems(items));
        }
        return mDatabase.itemDao().getStoriesLiveData();
    }

    public LiveData<List<Item>> getComments(final long storyId, final boolean refreshFromNetwork) {
        LiveData<List<Item>> commentsLiveData = mDatabase.itemDao().getCommentsLiveData(storyId);
        if (refreshFromNetwork) {
            mDatabase.itemDao().getItemFlowable(storyId)
                    // Get IDs of this story's comments
                    .filter(item -> item != null && item.getChildren() != null && !item.getChildren().isEmpty())
                    .flatMapIterable(Item::getChildren)
                    // Fetch comment details from network
                    .flatMap(mApi::getItem)
                    // Retry up to 3 times if the call fails
                    .retry(3)
                    .filter(item -> item != null)
                    // Update comment in the database
                    .subscribe(item -> mDatabase.itemDao().updateItem(item));
        }
        return commentsLiveData;
    }

    public LiveData<Item> getItemDetails(final long id, final boolean refreshFromNetwork) {
        if (refreshFromNetwork) {
            mApi.getItem(id)
                    .filter(item -> item != null)
                    .subscribe(item -> mDatabase.itemDao().updateItem(item));
        }
        return mDatabase.itemDao().getItemLiveData(id);
    }

}
