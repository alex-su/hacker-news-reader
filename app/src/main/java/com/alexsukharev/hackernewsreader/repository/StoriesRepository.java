package com.alexsukharev.hackernewsreader.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.alexsukharev.hackernewsreader.database.AppDatabase;
import com.alexsukharev.hackernewsreader.model.Item;
import com.alexsukharev.hackernewsreader.network.HackerNewsApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class StoriesRepository {

    private static final int PAGE_SIZE = 20;

    private final HackerNewsApi mApi;

    private final AppDatabase mDatabase;

    public StoriesRepository(@NonNull final AppDatabase database, @NonNull final HackerNewsApi api) {
        mDatabase = database;
        mApi = api;
    }

    /**
     * Loads a list of top stories refreshed from the network
     *
     * @return Stories LiveData to observe
     */
    public LiveData<List<Item>> getStories() {
        fetchTopStoriesFromNetwork();
        return mDatabase.itemDao().getStoriesLiveData();
    }

    /**
     * Refreshes the list of top stories from the network
     */
    public void refreshStories() {
        fetchTopStoriesFromNetwork();
    }

    /**
     * Loads the next page of stories
     *
     * @param lastSortOrder The sorting order of the last displayed item
     */
    public void loadMoreStories(final int lastSortOrder) {
        mDatabase.itemDao().getStoriesFlowable(lastSortOrder, PAGE_SIZE)
                .first(new ArrayList<>())
                .flatMap(this::fetchStoryListDetails)
                // Insert the list of items to the database
                .subscribe(items -> mDatabase.itemDao().updateItems(items));
    }

    /**
     * Loads item details from the database
     *
     * @param id Item id
     * @return Item LiveData to observe
     */
    public LiveData<Item> getStoryDetails(final long id) {
        return mDatabase.itemDao().getItemLiveData(id);
    }

    /*
     * ------------------- Private methods ---------------------
     */

    private void fetchTopStoriesFromNetwork() {
        mApi.getTopStories()
                // Retry up to 3 times if the call fails
                .retry(3)
                // Convert to a list of ordered items
                .map(this::getItemListOrdered)
                // Insert items to DB
                .doOnNext(items -> {
                    mDatabase.itemDao().deleteAll();
                    mDatabase.itemDao().insertItems(items);
                })
                // Pick items for the first page
                .flatMapIterable(items -> items)
                .take(PAGE_SIZE)
                .toList()
                // Fetch details of each item from network
                .flatMap(this::fetchStoryListDetails)
                // Insert the list of items to the database
                .subscribe(items -> mDatabase.itemDao().updateItems(items));
    }

    private Flowable<Item> fetchStoryDetails(final long id) {
        return mApi.getStory(id)
                // Retry up to 3 times if the call fails
                .retry(3)
                .filter(item -> item != null)
                .observeOn(Schedulers.computation())
                // Item parsed from json doesn't have sortOrder, we need to restore it from DB
                .map(this::restoreSortOrder);
    }

    private Single<List<Item>> fetchStoryListDetails(@NonNull final List<Item> itemList) {
        return Flowable.fromIterable(itemList)
                .flatMap(item -> fetchStoryDetails(item.getId()))
                .toList();
    }

    private List<Item> getItemListOrdered(@NonNull final List<Long> idList) {
        int sortOrder = 0;
        final List<Item> itemList = new ArrayList<>();
        for (final long id : idList) {
            final Item item = new Item(id, Item.TYPE_STORY, sortOrder++);
            itemList.add(item);
        }
        return itemList;
    }

    private Item restoreSortOrder(@NonNull final Item item) {
        final Item dbItem = mDatabase.itemDao().getItem(item.getId());
        if (dbItem != null) {
            item.setSortOrder(dbItem.getSortOrder());
        }
        return item;
    }

}
