package com.example.islam.movies.search_mvp;

import com.example.islam.movies.model.MoviesResponse;
import com.example.islam.movies.model.TvResponse;

public interface SearchView {
    void getSearchedMovies(MoviesResponse response);

    void getSearchedTv(TvResponse response);

    void showSearchProgress();

    void hideSearchProgress();

}
