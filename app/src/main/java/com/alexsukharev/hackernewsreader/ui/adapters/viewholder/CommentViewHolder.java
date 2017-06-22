package com.alexsukharev.hackernewsreader.ui.adapters.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.alexsukharev.hackernewsreader.databinding.CommentItemBinding;
import com.alexsukharev.hackernewsreader.model.Item;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    private CommentItemBinding mBinding;

    public CommentViewHolder(@NonNull final CommentItemBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(@NonNull final Item comment) {
        mBinding.setComment(comment);
    }
}
