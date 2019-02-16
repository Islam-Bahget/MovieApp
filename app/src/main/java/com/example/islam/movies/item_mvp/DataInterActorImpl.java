package com.example.islam.movies.item_mvp;

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

public class DataInterActorImpl implements DataInterActor {


    @Override
    public void getResponse(int page, String type, int reqType, final OnFetchEnded ended) {
        ApiService service = ApiClient.getMoviesClient().create(ApiService.class);


        CompositeDisposable disposable = new CompositeDisposable();

        switch (reqType) {
            case Const.MOVIES:


                disposable.add(service.getMovieList(type, Const.API_KEY, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<MoviesResponse>() {
                            @Override
                            public void onSuccess(MoviesResponse moviesResponse) {

                                ended.onData(moviesResponse);
                                Log.d("si", "Res " + moviesResponse.getResults().size());

                            }

                            @Override
                            public void onError(Throwable e) {
                                ended.onNowData(e.getMessage());

                            }
                        }));

                break;
            case Const.TRENDING:
                disposable.add(service.getTrending(type, Const.API_KEY, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<MoviesResponse>() {
                            @Override
                            public void onSuccess(MoviesResponse moviesResponse) {

                                ended.onData(moviesResponse);
                                Log.d("si", "Res " + moviesResponse.getResults().size());

                            }

                            @Override
                            public void onError(Throwable e) {
                                ended.onNowData(e.getMessage());

                            }
                        }));

                break;
            case Const.TV:
                disposable.add(service.getTvList(type, Const.API_KEY, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<TvResponse>() {
                            @Override
                            public void onSuccess(TvResponse tvResponse) {


                                ended.onTvData(tvResponse);
                            }

                            @Override
                            public void onError(Throwable e) {
                                ended.onNowData(e.getMessage());
                            }
                        }));


        }

    }
}
