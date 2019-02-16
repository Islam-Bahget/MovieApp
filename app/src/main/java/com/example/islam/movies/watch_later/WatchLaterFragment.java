package com.example.islam.movies.watch_later;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.islam.movies.moviedetails.MovieDetails;
import com.example.islam.movies.tv_details.TvDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WatchLaterFragment extends Fragment implements LaterView, LaterAdapter.OnLaterClickListener {
    @BindView(R.id.later_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.later_progress)
    ProgressBar progressBar;
    LaterAdapter adapter;
    LaterPresenter presenter;
    List<WatchLaterEntity> entityList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.watch_later, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.watch_later_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        presenter = new LaterPresenterImpl(getContext(), this);
        presenter.getFavourites();
    }

    @Override
    public void showLater(List<WatchLaterEntity> entityList) {

        this.entityList = entityList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new LaterAdapter(getContext(), this.entityList);
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);

    }

    @Override
    public void showProgress() {

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.clear_all:
                DataBaseSingleton.laterDao(getContext()).deleteLaterAll(this.entityList);
                adapter.notifyDataSetChanged();

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onLaterClicked(int position) {
        WatchLaterEntity entity = entityList.get(position);
        if (entity.getType().equals(Const.MOVIE_TYPE)) {
            startActivity(new Intent(getContext(), MovieDetails.class)
                    .putExtra("id", entity.getItemId()));
        } else {
            startActivity(new Intent(getContext(), TvDetails.class)
                    .putExtra("id", entity.getItemId()));
        }
    }
}
