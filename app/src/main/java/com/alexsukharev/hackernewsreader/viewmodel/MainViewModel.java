package com.alexsukharev.hackernewsreader.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.alexsukharev.hackernewsreader.di.Components;
import com.alexsukharev.hackernewsreader.model.Item;
import com.alexsukharev.hackernewsreader.repository.ItemsRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Stores and manages the list of @{{@link Item}} objects.
 * Extends @{@link ViewModel} to be lifecycle-aware and survive configuration changes.
 */

public class MainViewModel extends ViewModel {

    @Inject
    ItemsRepository mItemsRepository;

    public MainViewModel() {
        Components.getRepositoryComponent().inject(this);
    }

    public LiveData<List<Item>> getStories() {
        return mItemsRepository.getStories(true);
    }

}
