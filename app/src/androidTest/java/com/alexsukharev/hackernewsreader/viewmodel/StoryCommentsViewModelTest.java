package com.alexsukharev.hackernewsreader.viewmodel;

import android.support.test.runner.AndroidJUnit4;

import com.alexsukharev.hackernewsreader.model.Item;
import com.alexsukharev.hackernewsreader.repository.CommentsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class StoryCommentsViewModelTest extends BaseViewModelTest<StoryCommentsViewModel> {

    private Item mStory;

    private List<Item> mComments;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        try {
            // Fill the database with data that should be there before entering StoryCommentsActivity
            mViewModel.mStoriesRepository.refreshStories();
            Thread.sleep(200);
            mViewModel.mStoriesRepository.getStoryDetails(14612680L);
            Thread.sleep(200);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStoryDetails() throws InterruptedException {
        // Observe changes and load story details
        mViewModel.getStory().observeForever(story -> mStory = story);
        Thread.sleep(200);
        // Check the correct story is loaded
        assertNotNull(mStory);
        assertEquals(mStory.getId(), 14612680L);
    }

    @Test
    public void testLoadingComments() throws InterruptedException {
        // Observe changes and load the first page of items
        mViewModel.getComments().observeForever(comments -> mComments = comments);
        // Verify progress is shown
        assertEquals(mViewModel.isLoadingComments.get(), true);
        Thread.sleep(200);
        // Verify the list size
        assertEquals(mComments.size(), CommentsRepository.PAGE_SIZE);
        // Check sorting order
        assertEquals(mComments.get(0).getId(), 14613031L);
        assertEquals(mComments.get(CommentsRepository.PAGE_SIZE - 1).getId(), 14630357L);
        // Verify progress is not shown
        assertEquals(mViewModel.isLoadingComments.get(), false);

        // Try to load the second page
        mViewModel.loadMoreComments(CommentsRepository.PAGE_SIZE - 1);
        Thread.sleep(200);
        // Verify the list size
        assertEquals(mComments.size(), 37);

        mComments.clear();

        // Refresh data
        mViewModel.onRefreshComments();
        assertEquals(mViewModel.isLoadingComments.get(), true);
        Thread.sleep(200);
        // The first page of items should be loaded again
        assertEquals(mComments.size(), 37);
        assertEquals(mViewModel.isLoadingComments.get(), false);
    }

    @Override
    StoryCommentsViewModel createViewModel() {
        final StoryCommentsViewModel storyCommentsViewModel = new StoryCommentsViewModel();
        storyCommentsViewModel.setStoryId(14612680L);
        return storyCommentsViewModel;
    }
}