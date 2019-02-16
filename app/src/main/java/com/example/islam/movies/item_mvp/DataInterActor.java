package com.example.islam.movies.item_mvp;

import com.example.islam.movies.model.MoviesResponse;
import com.example.islam.movies.model.TvResponse;

public interface DataInterActor {
    interface OnFetchEnded {
        void onData(MoviesResponse response);

        void onTvData(TvResponse response);

        void onNowData(String err);
    }

    void getResponse(int page, String type, int reqType, OnFetchEnded ended);
}
