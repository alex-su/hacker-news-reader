package com.alexsukharev.hackernewsreader.ui.activities;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.alexsukharev.hackernewsreader.R;
import com.alexsukharev.hackernewsreader.databinding.ActivityMainBinding;
import com.alexsukharev.hackernewsreader.ui.adapters.StoriesAdapter;
import com.alexsukharev.hackernewsreader.util.EndlessRecyclerOnScrollListener;
import com.alexsukharev.hackernewsreader.viewmodel.MainViewModel;

public class MainActivity extends LifecycleActivity {

    private ActivityMainBinding mBinding;

    private StoriesAdapter mStoriesAdapter;

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Returns a cached ViewModel or creates a new one
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mBinding.setViewModel(mViewModel);

        initStoryList();
    }

    private void initStoryList() {
        mStoriesAdapter = new StoriesAdapter(mViewModel);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.recyclerView.setAdapter(mStoriesAdapter);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mStoriesAdapter, layoutManager,
                () -> mViewModel.loadMore(mStoriesAdapter.getLastSortOrder())));

        // Observe changes of the list of stories
        mViewModel.getStories().observe(this, stories -> {
            // Update UI
            if (stories != null) {
                mStoriesAdapter.setData(stories);
            }
        });
    }

}
