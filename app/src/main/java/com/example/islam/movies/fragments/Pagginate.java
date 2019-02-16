package com.example.islam.movies.fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class Pagginate extends RecyclerView.OnScrollListener {
    GridLayoutManager gridLayoutManager;

    public Pagginate(GridLayoutManager gridLayoutManager) {
        this.gridLayoutManager = gridLayoutManager;
    }


    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = gridLayoutManager.getChildCount();
        int totalItemCount = gridLayoutManager.getItemCount();
        int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                loadMore();
            }
        }


    }

    protected abstract void loadMore();

    public abstract int getTotalPages();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();
}
