package com.example.islam.movies.search_mvp;

import android.util.Log;

import com.example.islam.movies.Const;
import com.example.islam.movies.data_repository.ApiClient;
import com.example.islam.movies.data_repository.ApiService;
import com.example.islam.movies.model.MoviesResponse;
import com.example.islam.movies.model.TvResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchMoviesInterActorImpl implements SearchInterActor {


    @Override
    public void getSearchedMovies(String q, OnSearchFinished searchFinished, int type) {
        CompositeDisposable disposable = new CompositeDisposable();
        ApiService apiService = ApiClient.getMoviesClient().create(ApiService.class);

        switch (type) {
            case Const.MOVIES:
                disposable.add(apiService.searchMovie(Const.API_KEY, q).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<MoviesResponse>() {
                            @Override
                            public void onSuccess(MoviesResponse response) {

                                Log.d("size", "Size is search " + response.getResults().size());
                                searchFinished.onMoviesSearched(response);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }));
                break;
            case Const.TV:
                disposable.add(apiService.searchTv(Const.API_KEY, q)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<TvResponse>() {
                            @Override
                            public void onSuccess(TvResponse response) {

                                searchFinished.onTvSearched(response);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }));
        }

    }
}
