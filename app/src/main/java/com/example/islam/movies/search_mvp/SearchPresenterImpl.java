package com.example.islam.movies.search_mvp;

import com.example.islam.movies.model.MoviesResponse;
import com.example.islam.movies.model.TvResponse;

public class SearchPresenterImpl implements SearchPresenter, SearchInterActor.OnSearchFinished {

    private SearchView searchView;
    private SearchInterActor interActor;

    public SearchPresenterImpl(SearchView searchView) {
        this.searchView = searchView;
        interActor = new SearchMoviesInterActorImpl();
    }


    @Override
    public void onMoviesSearched(MoviesResponse response) {
        if (searchView != null) {
            //   searchView.hideSearchProgress();
            searchView.getSearchedMovies(response);
        }

    }

    @Override
    public void onTvSearched(TvResponse response) {
        if (searchView != null) {
            searchView.getSearchedTv(response);
        }
    }

    @Override
    public void getSearchMovies(String q, int type) {

        if (searchView != null) {
            //   searchView.showSearchProgress();
            interActor.getSearchedMovies(q, this, type);
        }
    }

    @Override
    public void destroy() {

        if (searchView != null) {
            searchView = null;
        }
    }
}
