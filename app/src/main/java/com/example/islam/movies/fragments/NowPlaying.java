package com.example.islam.movies.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.islam.movies.Const;
import com.example.islam.movies.R;
import com.example.islam.movies.model.Movie;
import com.example.islam.movies.model.MoviesResponse;
import com.example.islam.movies.model.TvResponse;
import com.example.islam.movies.moviedetails.MovieDetails;
import com.example.islam.movies.item_mvp.DataPresenter;
import com.example.islam.movies.item_mvp.DataPresenterImpl;
import com.example.islam.movies.item_mvp.DataView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NowPlaying extends Fragment implements DataView, ReAdapter.OnMovieClickListener {
    @BindView(R.id.movies_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;
    DataPresenter presenter;
    @BindView(R.id.swipe)
    SwipeRefreshLayout refreshLayout;


    private int TOTAL_PAGES = 1;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;

    ArrayList<Movie> movies;
    ReAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_desgin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter = new DataPresenterImpl(this);
        movies = new ArrayList<>();
        adapter = new ReAdapter(getActivity(), Const.MOVIES);
        adapter.setMovieList(movies);
        adapter.setOnMovieClicked(this);
        refreshLayout.setOnRefreshListener(this::loadFirstPage);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);


        recyclerView.addOnScrollListener(new Pagginate(gridLayoutManager) {
            @Override
            protected void loadMore() {
                isLoading = true;
                currentPage += 1;

                new Handler().postDelayed(() -> loadNextPage(), 1000);

            }

            @Override
            public int getTotalPages() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        new Handler().postDelayed(this::loadFirstPage, 1000);

    }


    private void loadNextPage() {
        presenter.getMovies(currentPage, "top_rated", Const.MOVIES);

    }


    @Override
    public void loadData(MoviesResponse response) {

        refreshLayout.setRefreshing(false);
        isLoading = false;
        TOTAL_PAGES = response.getTotalPages();
        movies.addAll(response.getResults());
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onNoData(String error) {
        refreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), "No Movies", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {
        if (progressBar.getVisibility() == View.VISIBLE)
            progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void loadTvData(TvResponse response) {

    }

    @Override
    public void showProgress() {
        if (progressBar.getVisibility() == View.INVISIBLE)
            progressBar.setVisibility(View.VISIBLE);

    }

    private void loadFirstPage() {

        presenter.getMovies(currentPage, "now_playing", Const.MOVIES);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void onMovieClicked(int position) {
        startActivity(new Intent(getContext(), MovieDetails.class).putExtra("id", movies.get(position).getId()));

    }
}