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

public class CommentsRepository {

    private static final int PAGE_SIZE = 20;

    private final HackerNewsApi mApi;

    private final AppDatabase mDatabase;

    public CommentsRepository(@NonNull final AppDatabase database, @NonNull final HackerNewsApi api) {
        mDatabase = database;
        mApi = api;
    }

    /**
     * Loads a list of 20 first comments of the story refreshed from network
     *
     * @return Comments LiveData to observe
     */
    public LiveData<List<Item>> getComments(final long storyId) {
        fetchCommentsFromNetwork(storyId);
        return mDatabase.itemDao().getCommentsLiveData(storyId);
    }

    /**
     * Refreshes the list of comments related to the story from the network
     */
    public void refreshComments(final long storyId) {
        fetchCommentsFromNetwork(storyId);
    }

    /**
     * Loads the next page of comments
     *
     * @param lastSortOrder The sorting order of the last displayed item
     */
    public void loadMoreComments(final long storyId, final int lastSortOrder) {
        mDatabase.itemDao().getCommentsFlowable(storyId, lastSortOrder, PAGE_SIZE)
                .first(new ArrayList<>())
                .flatMap(this::fetchCommentListDetails)
                // Insert the list of items to the database
                .subscribe(items -> mDatabase.itemDao().updateItems(items));
    }

    private void fetchCommentsFromNetwork(final long storyId) {
        mDatabase.itemDao().getItemFlowable(storyId)
                .first(new Item(0))
                // Get IDs of this story's comments
                .filter(item -> item != null && item.getKids() != null && !item.getKids().isEmpty())
                .map(item -> getItemListOrdered(item.getKids(), storyId))
                .doOnSuccess(items -> mDatabase.itemDao().insertItems(items))
                // Pick items for the first page
                .flattenAsFlowable(items -> items)
                .take(PAGE_SIZE)
                .toList()
                // Fetch comment details from network
                .flatMap(this::fetchCommentListDetails)
                // Update comments in the database
                .subscribe(items -> mDatabase.itemDao().updateItems(items));
    }

    private Flowable<Item> fetchCommentDetails(final long id) {
        return mApi.getComment(id)
                // Retry up to 3 times if the call fails
                .retry(3)
                .filter(item -> item != null)
                .observeOn(Schedulers.computation())
                // Item parsed from json doesn't have sortOrder, we need to restore it from DB
                .map(this::restoreSortOrder);
    }

    private Single<List<Item>> fetchCommentListDetails(@NonNull final List<Item> commentList) {
        return Flowable.fromIterable(commentList)
                .flatMap(item -> fetchCommentDetails(item.getId()))
                .toList();
    }

    private List<Item> getItemListOrdered(@NonNull final List<Long> idList, final long parentId) {
        int sortOrder = 0;
        final List<Item> itemList = new ArrayList<>();
        for (final long id : idList) {
            final Item item = new Item(id, Item.TYPE_COMMENT, sortOrder++);
            item.setParent(parentId);
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
