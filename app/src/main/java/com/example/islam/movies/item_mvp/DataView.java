package com.example.islam.movies.item_mvp;

import com.example.islam.movies.model.MoviesResponse;
import com.example.islam.movies.model.TvResponse;

public interface DataView {
    void loadData(MoviesResponse response);

    void loadTvData(TvResponse response);

    void onNoData(String error);

    void hideProgress();

    void showProgress();
}
