package com.example.islam.movies.item_details_mvp;


import com.example.islam.movies.model.MovieDetailsResponse;
import com.example.islam.movies.model.TvDetailsResponse;
import com.example.islam.movies.model.VideoResponse;

public class ItemDetailsPresenterImpl implements ItemDetailsPresenter, ItemDetailsInterActor.OnCastFinished {

    private ItemDetailsInterActor interActor;
    private ItemDetailsView view;

    public ItemDetailsPresenterImpl(int id, ItemDetailsView view) {
        this.view = view;
        interActor = new ItemDetailsInterActorImpl(id);
    }

    @Override
    public void getMovieDetails(int type) {
        if (view != null) {
            view.showProgress();
            interActor.getMovieDetails(this, type);
        }

    }

    @Override
    public void getTrailers(int type) {

        if (view != null) {
            interActor.getTrailers(this, type);
        }
    }

    @Override
    public void destroy() {

        if (view != null) {
            view = null;
        }
    }

    @Override
    public void onSuccess(MovieDetailsResponse movieDetailsResponse) {

        if (view != null) {

            view.showMovieData(movieDetailsResponse);
            view.hideProgress();
        }
    }

    @Override
    public void onTvSuccess(TvDetailsResponse response) {
        if (view != null) {
            view.hideProgress();
            view.showTvData(response);
        }
    }

    @Override
    public void onTrailersLoaded(VideoResponse response) {
        if (view != null) {
            view.showTrailers(response);
        }
    }

    @Override
    public void onError(String e) {
        if (view != null) {
            view.showError(e);
            view.hideProgress();
        }
    }

}
