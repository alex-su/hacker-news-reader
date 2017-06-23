package com.alexsukharev.hackernewsreader.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import com.alexsukharev.hackernewsreader.di.Components;
import com.alexsukharev.hackernewsreader.model.Item;
import com.alexsukharev.hackernewsreader.repository.CommentsRepository;
import com.alexsukharev.hackernewsreader.repository.StoriesRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Stores and manages the list of @{{@link Item}} objects.
 * Extends @{@link ViewModel} to be lifecycle-aware and survive configuration changes.
 */

public class StoryCommentsViewModel extends ViewModel {

    @Inject
    StoriesRepository mStoriesRepository;

    @Inject
    CommentsRepository mCommentsRepository;

    /**
     * View decides to show or not to show progress based on this value
     */
    public ObservableBoolean isLoadingComments = new ObservableBoolean(false);

    /**
     * Hides loading progress when items are received
     */
    private Observer<List<Item>> mCommentsObserver = comments -> {
        if (comments != null && !comments.isEmpty()) {
            isLoadingComments.set(false);
        }
    };

    private Observer<Item> mStoryObserver = story -> {
        // The story has no comments, we should hide the progress
        if (story != null && (story.getKids() == null || story.getKids().isEmpty())) {
            isLoadingComments.set(false);
        }
    };

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
            mStoryLiveData = mStoriesRepository.getStoryDetails(mStoryId);
            mStoryLiveData.observeForever(mStoryObserver);
        }
        return mStoryLiveData;
    }

    public LiveData<List<Item>> getComments() {
        // Check for cached LiveData. If null, get a new one from the repository
        if (mCommentsLiveData == null) {
            isLoadingComments.set(true);
            mCommentsLiveData = mCommentsRepository.getComments(mStoryId);
            mCommentsLiveData.observeForever(mCommentsObserver);
        }
        return mCommentsLiveData;
    }

    /**
     * Updates the list of comments from network
     */
    public void onRefreshComments() {
        isLoadingComments.set(true);
        mCommentsRepository.refreshComments(mStoryId);
    }

    public void loadMoreComments(final int lastSortOrder) {
        mCommentsRepository.loadMoreComments(mStoryId, lastSortOrder);
    }

    @Override
    protected void onCleared() {
        if (mStoryLiveData != null) {
            mStoryLiveData.removeObserver(mStoryObserver);
        }
        if (mCommentsLiveData != null) {
            mCommentsLiveData.removeObserver(mCommentsObserver);
        }
    }

}
