package com.alexsukharev.hackernewsreader.viewmodel;

import android.support.test.runner.AndroidJUnit4;

import com.alexsukharev.hackernewsreader.model.Item;
import com.alexsukharev.hackernewsreader.repository.StoriesRepository;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MainViewModelTest extends BaseViewModelTest<MainViewModel> {

    private List<Item> mStories;

    @Test
    public void testLoadingData() throws InterruptedException {
        // Observe changes and load the first page of items
        mViewModel.getStories().observeForever(stories -> mStories = stories);
        // Verify progress is shown
        assertEquals(mViewModel.isLoading.get(), true);
        Thread.sleep(200);
        // Verify the list size
        assertEquals(mStories.size(), StoriesRepository.PAGE_SIZE);
        // Check sorting order
        assertEquals(mStories.get(0).getId(), 14612680L);
        assertEquals(mStories.get(StoriesRepository.PAGE_SIZE - 1).getId(), 14610882L);
        // Verify progress is not shown
        assertEquals(mViewModel.isLoading.get(), false);

        // Try to load the second page
        mViewModel.loadMore(StoriesRepository.PAGE_SIZE - 1);
        Thread.sleep(200);
        // Verify the list size
        assertEquals(mStories.size(), StoriesRepository.PAGE_SIZE * 2);

        // Refresh data
        mViewModel.onRefresh();
        assertEquals(mViewModel.isLoading.get(), true);
        Thread.sleep(200);
        // The first page of items should be loaded again
        assertEquals(mStories.size(), StoriesRepository.PAGE_SIZE);
        assertEquals(mViewModel.isLoading.get(), false);
    }

    @Override
    MainViewModel createViewModel() {
        return new MainViewModel();
    }
}