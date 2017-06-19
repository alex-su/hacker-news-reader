package com.alexsukharev.hackernewsreader.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * https://android.jlelse.eu/recyclerview-with-endlessscroll-2c503008522f
 */

public class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private static final int VISIBLE_THRESHOLD = 5;

    /**
     * The total number of items in the dataset after the last load
     */
    private int mPreviousTotal = 0;

    /**
     * True if we are still waiting for the last set of data to load.
     */
    private boolean mLoading = true;

    private LinearLayoutManager mLinearLayoutManager;

    private RecyclerView.Adapter mAdapter;

    private Callback mCallback;

    public EndlessRecyclerOnScrollListener(@NonNull final RecyclerView.Adapter adapter, @NonNull final LinearLayoutManager linearLayoutManager, @NonNull final Callback callback) {
        mLinearLayoutManager = linearLayoutManager;
        mAdapter = adapter;
        mCallback = callback;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = mLinearLayoutManager.findLastVisibleItemPosition() - mLinearLayoutManager.findFirstVisibleItemPosition();
        int totalItemCount = mAdapter.getItemCount();
        int firstVisibleItem = mLinearLayoutManager.findFirstCompletelyVisibleItemPosition();

        if (mLoading) {
            if (totalItemCount != mPreviousTotal) {
                mLoading = false;
                mPreviousTotal = totalItemCount;
            }
        }
        if (!mLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
            // End has been reached
            mCallback.onLoadMore();
            mLoading = true;
        }
    }

    public interface Callback {

        void onLoadMore();

    }
}
