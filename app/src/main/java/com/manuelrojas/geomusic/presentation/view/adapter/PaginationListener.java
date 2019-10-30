package com.manuelrojas.geomusic.presentation.view.adapter;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationListener extends RecyclerView.OnScrollListener {

    private static final String TAG = "PaginationListener";
    private LinearLayoutManager layoutManager;

    /**
     * Set scrolling threshold here (for now i'm assuming 20 item in one page)
     */
    private static final int PAGE_SIZE = 20;

    public PaginationListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                loadMoreItems();
            }
        }
    }

    protected abstract void loadMoreItems();
    public abstract int getTotalPageCount();
    public abstract boolean isLastPage();
    public abstract boolean isLoading();
}