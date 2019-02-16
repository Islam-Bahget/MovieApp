package com.example.islam.movies.search_mvp;

public interface SearchPresenter {
    void getSearchMovies(String q, int type);

    void destroy();
}
