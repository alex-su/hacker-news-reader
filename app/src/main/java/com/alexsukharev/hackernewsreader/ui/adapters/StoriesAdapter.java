package com.alexsukharev.hackernewsreader.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alexsukharev.hackernewsreader.R;
import com.alexsukharev.hackernewsreader.databinding.StoryItemBinding;
import com.alexsukharev.hackernewsreader.model.Item;
import com.alexsukharev.hackernewsreader.ui.adapters.viewholder.StoryViewHolder;
import com.alexsukharev.hackernewsreader.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class StoriesAdapter extends RecyclerView.Adapter<StoryViewHolder> {

    private List<Item> mStories = new ArrayList<>();
    private MainViewModel mViewModel;

    public StoriesAdapter(@NonNull final MainViewModel viewModel) {
        super();
        setHasStableIds(true);
        mViewModel = viewModel;
    }

    @Override
    public StoryViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final StoryItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.story_item, parent, false);
        binding.setViewModel(mViewModel);
        return new StoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final StoryViewHolder holder, final int position) {
        holder.bind(mStories.get(position));
    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }

    @Override
    public long getItemId(final int position) {
        return mStories.get(position).getId();
    }

    public void setData(@NonNull final List<Item> storyList) {
        mStories.clear();
        mStories.addAll(storyList);
        notifyDataSetChanged();
    }
}
