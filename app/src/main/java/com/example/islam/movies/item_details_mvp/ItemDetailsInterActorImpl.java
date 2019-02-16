package com.example.islam.movies.item_details_mvp;

import com.example.islam.movies.Const;
import com.example.islam.movies.data_repository.ApiClient;
import com.example.islam.movies.data_repository.ApiService;
import com.example.islam.movies.model.MovieDetailsResponse;
import com.example.islam.movies.model.TvDetailsResponse;
import com.example.islam.movies.model.VideoResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ItemDetailsInterActorImpl implements ItemDetailsInterActor {

    private CompositeDisposable disposable;

    private int id;


    ItemDetailsInterActorImpl(int id) {
        disposable = new CompositeDisposable();
        this.id = id;
    }

    @Override
    public void getMovieDetails(OnCastFinished onCastFinished, int type) {
        ApiService apiService = ApiClient.getMoviesClient().create(ApiService.class);

        switch (type) {
            case Const.MOVIES:
                disposable.add(apiService.getMovieDetails(id, Const.API_KEY, "credits")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<MovieDetailsResponse>() {
                            @Override
                            public void onSuccess(MovieDetailsResponse movieDetailsResponse) {

                                onCastFinished.onSuccess(movieDetailsResponse);
                            }

                            @Override
                            public void onError(Throwable e) {

                                onCastFinished.onError(e.toString());
                            }
                        }));
                break;
            case Const.TV:
                disposable.add(apiService.getTvDetails(id, Const.API_KEY, "credits")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<TvDetailsResponse>() {
                            @Override
                            public void onSuccess(TvDetailsResponse response) {
                                onCastFinished.onTvSuccess(response);
                            }

                            @Override
                            public void onError(Throwable e) {
                                onCastFinished.onError(e.getMessage());

                            }
                        })
                );
        }


    }

    @Override
    public void getTrailers(OnCastFinished ended, int type) {
        ApiService service = ApiClient.getMoviesClient().create(ApiService.class);

        switch (type) {
            case Const.MOVIES:
                disposable.add(service.getTrailers(id, Const.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<VideoResponse>() {
                            @Override
                            public void onSuccess(VideoResponse videoResponse) {

                                ended.onTrailersLoaded(videoResponse);
                            }

                            @Override
                            public void onError(Throwable e) {


                            }
                        }));
                break;
            case Const.TV:
                disposable.add(service.getTvTrailers(id, Const.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<VideoResponse>() {
                            @Override
                            public void onSuccess(VideoResponse videoResponse) {

                                ended.onTrailersLoaded(videoResponse);
                            }

                            @Override
                            public void onError(Throwable e) {


                            }
                        }));

        }

    }


}
