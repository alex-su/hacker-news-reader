package com.alexsukharev.hackernewsreader.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alexsukharev.hackernewsreader.R;
import com.alexsukharev.hackernewsreader.databinding.CommentItemBinding;
import com.alexsukharev.hackernewsreader.model.Item;
import com.alexsukharev.hackernewsreader.ui.adapters.viewholder.CommentViewHolder;
import com.alexsukharev.hackernewsreader.viewmodel.StoryCommentsViewModel;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private List<Item> mComments = new ArrayList<>();
    private StoryCommentsViewModel mViewModel;

    public CommentsAdapter(@NonNull final StoryCommentsViewModel viewModel) {
        super();
        setHasStableIds(true);
        mViewModel = viewModel;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final CommentItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.comment_item, parent, false);
        binding.setViewModel(mViewModel);
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final CommentViewHolder holder, final int position) {
        holder.bind(mComments.get(position));
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    @Override
    public long getItemId(final int position) {
        return mComments.get(position).getId();
    }

    public void setData(@NonNull final List<Item> storyList) {
        mComments.clear();
        mComments.addAll(storyList);
        notifyDataSetChanged();
    }

    public int getLastSortOrder() {
        return mComments.size() > 0 ? mComments.get(mComments.size() -1).getSortOrder() : 0;
    }
}
