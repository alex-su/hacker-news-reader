package com.alexsukharev.hackernewsreader.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.view.View;

import com.alexsukharev.hackernewsreader.di.Components;
import com.alexsukharev.hackernewsreader.model.Item;
import com.alexsukharev.hackernewsreader.repository.ItemsRepository;
import com.alexsukharev.hackernewsreader.ui.activities.StoryCommentsActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * Stores and manages the list of @{{@link Item}} objects.
 * Extends @{@link ViewModel} to be lifecycle-aware and survive configuration changes.
 */

public class MainViewModel extends ViewModel {

    @Inject
    ItemsRepository mItemsRepository;

    /**
     * View decides to show or not to show progress based on this value
     */
    public ObservableBoolean isLoading = new ObservableBoolean(false);

    /**
     * A holder for the list of items. Receives automatic updates when the data changes.
     */
    private LiveData<List<Item>> mStoriesLiveData;

    public MainViewModel() {
        Components.getRepositoryComponent().inject(this);
    }

    public LiveData<List<Item>> getStories() {
        // Check for cached LiveData. If null, get a new one from the repository
        if (mStoriesLiveData == null) {
            isLoading.set(true);
            mStoriesLiveData = mItemsRepository.getStories();
        }
        return mStoriesLiveData;
    }

    /**
     * Updates the list of stories from network
     */
    public void onRefresh() {
        isLoading.set(true);
        mItemsRepository.refreshStories();
    }

    public void loadMore(final int lastSortOrder) {
        mItemsRepository.loadMoreStories(lastSortOrder);
    }

    public void onStoryClicked(@NonNull final View view, @NonNull final Item story) {
        StoryCommentsActivity.start(view.getContext(), story.getId());
    }

}
