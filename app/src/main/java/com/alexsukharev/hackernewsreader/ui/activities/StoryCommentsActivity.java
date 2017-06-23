package com.alexsukharev.hackernewsreader.ui.activities;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;

import com.alexsukharev.hackernewsreader.R;
import com.alexsukharev.hackernewsreader.databinding.ActivityStoryCommentsBinding;
import com.alexsukharev.hackernewsreader.ui.adapters.CommentsAdapter;
import com.alexsukharev.hackernewsreader.util.EndlessRecyclerOnScrollListener;
import com.alexsukharev.hackernewsreader.viewmodel.StoryCommentsViewModel;

public class StoryCommentsActivity extends LifecycleActivity {

    private static final String EXTRA_STORY_ID = "story_id";

    public static void start(@NonNull final Context context, final long storyId) {
        final Intent intent = new Intent(context, StoryCommentsActivity.class);
        intent.putExtra(EXTRA_STORY_ID, storyId);
        context.startActivity(intent);
    }

    private ActivityStoryCommentsBinding mBinding;

    private CommentsAdapter mCommentsAdapter;

    private StoryCommentsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_story_comments);

        // Returns a cached ViewModel or creates a new one
        mViewModel = ViewModelProviders.of(this).get(StoryCommentsViewModel.class);
        mViewModel.setStoryId(getIntent().getLongExtra(EXTRA_STORY_ID, -1));
        mBinding.setViewModel(mViewModel);

        initStoryDetails();
        initCommentList();
    }

    private void initStoryDetails() {
        mViewModel.getStory().observe(this, story -> mBinding.setStory(story));
    }

    private void initCommentList() {
        mCommentsAdapter = new CommentsAdapter(mViewModel);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.recyclerView.setAdapter(mCommentsAdapter);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mCommentsAdapter, layoutManager,
                () -> mViewModel.loadMoreComments(mCommentsAdapter.getLastSortOrder())));

        // Observe changes of the list of comments
        mViewModel.getComments().observe(this, comments -> {
            // Update UI
            if (comments != null) {
                mCommentsAdapter.setData(comments);
            }
        });
    }

}
