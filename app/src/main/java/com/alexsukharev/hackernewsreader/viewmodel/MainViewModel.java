package com.alexsukharev.hackernewsreader.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.view.View;

import com.alexsukharev.hackernewsreader.di.Components;
import com.alexsukharev.hackernewsreader.model.Item;
import com.alexsukharev.hackernewsreader.repository.StoriesRepository;
import com.alexsukharev.hackernewsreader.ui.activities.StoryCommentsActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * Stores and manages the list of @{{@link Item}} objects.
 * Extends @{@link ViewModel} to be lifecycle-aware and survive configuration changes.
 */

public class MainViewModel extends ViewModel {

    @Inject
    StoriesRepository mStoriesRepository;

    /**
     * View decides to show or not to show progress based on this value
     */
    public ObservableBoolean isLoading = new ObservableBoolean(false);

    /**
     * A holder for the list of items. Receives automatic updates when the data changes.
     */
    private LiveData<List<Item>> mStoriesLiveData;

    /**
     * Hides loading progress when items are received
     */
    private Observer<List<Item>> mStoriesObserver = stories -> isLoading.set(stories != null && stories.isEmpty());

    public MainViewModel() {
        Components.getRepositoryComponent().inject(this);
    }

    public LiveData<List<Item>> getStories() {
        // Check for cached LiveData. If null, get a new one from the repository
        if (mStoriesLiveData == null) {
            isLoading.set(true);
            mStoriesLiveData = mStoriesRepository.getStories();
            mStoriesLiveData.observeForever(mStoriesObserver);
        }
        return mStoriesLiveData;
    }

    /**
     * Updates the list of stories from network
     */
    public void onRefresh() {
        isLoading.set(true);
        mStoriesRepository.refreshStories();
    }

    public void loadMore(final int lastSortOrder) {
        mStoriesRepository.loadMoreStories(lastSortOrder);
    }

    public void onStoryClicked(@NonNull final View view, @NonNull final Item story) {
        StoryCommentsActivity.start(view.getContext(), story.getId());
    }

    @Override
    protected void onCleared() {
        if (mStoriesLiveData != null) {
            mStoriesLiveData.removeObserver(mStoriesObserver);
        }
    }

}
