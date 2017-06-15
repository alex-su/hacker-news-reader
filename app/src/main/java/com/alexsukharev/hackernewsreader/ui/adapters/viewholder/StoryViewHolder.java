package com.alexsukharev.hackernewsreader.ui.adapters.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.alexsukharev.hackernewsreader.databinding.StoryItemBinding;
import com.alexsukharev.hackernewsreader.model.Item;

public class StoryViewHolder extends RecyclerView.ViewHolder {

    private StoryItemBinding mBinding;

    public StoryViewHolder(@NonNull final StoryItemBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(@NonNull final Item story) {
        mBinding.setStory(story);
    }
}
