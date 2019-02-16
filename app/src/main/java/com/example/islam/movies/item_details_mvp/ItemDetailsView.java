package com.example.islam.movies.item_details_mvp;


import com.example.islam.movies.model.MovieDetailsResponse;
import com.example.islam.movies.model.TvDetailsResponse;
import com.example.islam.movies.model.VideoResponse;

public interface ItemDetailsView {
    void showProgress();

    void hideProgress();

    void showMovieData(MovieDetailsResponse response);

    void showTvData(TvDetailsResponse response);

    void showTrailers(VideoResponse response);

    void showError(String error);

}
