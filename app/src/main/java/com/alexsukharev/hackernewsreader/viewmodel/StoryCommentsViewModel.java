package com.alexsukharev.hackernewsreader.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import com.alexsukharev.hackernewsreader.di.Components;
import com.alexsukharev.hackernewsreader.model.Item;
import com.alexsukharev.hackernewsreader.repository.ItemsRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Stores and manages the list of @{{@link Item}} objects.
 * Extends @{@link ViewModel} to be lifecycle-aware and survive configuration changes.
 */

public class StoryCommentsViewModel extends ViewModel {

    @Inject
    ItemsRepository mItemsRepository;

    /**
     * View decides to show or not to show progress based on this value
     */
    public ObservableBoolean isLoadingComments = new ObservableBoolean(false);

    /**
     * A holder for the list of items. Receives automatic updates when the data changes.
     */
    private LiveData<List<Item>> mCommentsLiveData;

    private LiveData<Item> mStoryLiveData;

    private long mStoryId;

    public StoryCommentsViewModel() {
        Components.getRepositoryComponent().inject(this);
    }

    public void setStoryId(final long storyId) {
        mStoryId = storyId;
    }

    public LiveData<Item> getStory() {
        // Check for cached LiveData. If null, get a new one from the repository
        if (mStoryLiveData == null) {
            mStoryLiveData = mItemsRepository.getItemDetails(mStoryId);
        }
        return mStoryLiveData;
    }

    public LiveData<List<Item>> getComments() {
        // Check for cached LiveData. If null, get a new one from the repository
        if (mCommentsLiveData == null) {
            mCommentsLiveData = mItemsRepository.getComments(mStoryId);
        }
        return mCommentsLiveData;
    }

    /**
     * Updates the list of comments from network
     */
    public void onRefreshComments() {
        isLoadingComments.set(true);
        mItemsRepository.refreshComments(mStoryId);
    }

    public void loadMoreComments(final int lastSortOrder) {
        mItemsRepository.loadMoreComments(mStoryId, lastSortOrder);
    }

}
