package com.example.islam.movies.search_mvp;

import com.example.islam.movies.model.MoviesResponse;
import com.example.islam.movies.model.TvResponse;

public interface SearchInterActor {
    interface OnSearchFinished {
        void onMoviesSearched(MoviesResponse response);

        void onTvSearched(TvResponse response);
    }

    void getSearchedMovies(String q, OnSearchFinished searchFinished, int type);
}
