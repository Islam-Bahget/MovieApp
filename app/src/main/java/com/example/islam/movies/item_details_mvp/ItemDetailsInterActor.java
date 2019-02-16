package com.example.islam.movies.item_details_mvp;


import com.example.islam.movies.model.MovieDetailsResponse;
import com.example.islam.movies.model.TvDetailsResponse;
import com.example.islam.movies.model.VideoResponse;

interface ItemDetailsInterActor {

    interface OnCastFinished {

        void onSuccess(MovieDetailsResponse movieDetailsResponse);

        void onTvSuccess(TvDetailsResponse response);

        void onTrailersLoaded(VideoResponse response);

        void onError(String e);
    }

    void getMovieDetails(OnCastFinished onCastFinished, int type);

    void getTrailers(OnCastFinished ended, int type);

}
