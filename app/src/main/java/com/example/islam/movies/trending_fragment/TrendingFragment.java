package com.example.islam.movies.trending_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.islam.movies.Const;
import com.example.islam.movies.R;
import com.example.islam.movies.fragments.Pagginate;
import com.example.islam.movies.fragments.ReAdapter;
import com.example.islam.movies.model.Movie;
import com.example.islam.movies.model.MoviesResponse;
import com.example.islam.movies.model.TvResponse;
import com.example.islam.movies.moviedetails.MovieDetails;
import com.example.islam.movies.item_mvp.DataPresenter;
import com.example.islam.movies.item_mvp.DataPresenterImpl;
import com.example.islam.movies.item_mvp.DataView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrendingFragment extends Fragment implements ReAdapter.OnMovieClickListener, DataView {

    @BindView(R.id.movies_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    ReAdapter adapter;

    private int TOTAL_PAGES = 1;
    private String type = "day";

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;
    List<Movie> trendingList;

    DataPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_desgin, container, false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        presenter = new DataPresenterImpl(this);
        trendingList = new ArrayList<>();

        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(manager);
        adapter = new ReAdapter(getContext(), Const.MOVIES);
        adapter.setMovieList(trendingList);
        recyclerView.setAdapter(adapter);
        adapter.setOnMovieClicked(this);

        recyclerView.addOnScrollListener(new Pagginate(manager) {
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

    @Override
    public void showProgress() {

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadData(MoviesResponse response) {

        isLoading = false;
        TOTAL_PAGES = response.getTotalPages();

        adapter.addAll(response.getResults());
        adapter.notifyDataSetChanged();


    }

    @Override
    public void loadTvData(TvResponse response) {

    }


    private void loadNextPage() {
        presenter.getMovies(currentPage, type, Const.TRENDING);

    }

    private void loadFirstPage() {

        presenter.getMovies(currentPage, type, Const.TRENDING);
    }

    @Override
    public void onNoData(String error) {

    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onMovieClicked(int position) {

        startActivity(new Intent(getContext(), MovieDetails.class)
                .putExtra("id", trendingList.get(position).getId()));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.trend, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.by_day:
                adapter.clear();
                currentPage = PAGE_START;
                type = "day";
                loadFirstPage();
                return true;
            case R.id.by_week:
                adapter.clear();
                currentPage = PAGE_START;
                type = "week";
                loadFirstPage();
        }
        return super.onOptionsItemSelected(item);
    }
}
