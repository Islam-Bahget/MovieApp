package com.example.islam.movies.item_mvp;

import com.example.islam.movies.model.MoviesResponse;
import com.example.islam.movies.model.TvResponse;

public class DataPresenterImpl implements DataPresenter, DataInterActor.OnFetchEnded {
    private DataInterActor interActor;
    private DataView dataView;

    public DataPresenterImpl(DataView dataView) {
        this.dataView = dataView;
        interActor = new DataInterActorImpl();
    }


    @Override
    public void getMovies(int page, String type, int reqType) {
        if (dataView != null) {
            dataView.showProgress();
            interActor.getResponse(page, type, reqType, this);
        }

    }

    @Override
    public void destroy() {
        if (dataView != null) {
            dataView = null;
        }

    }

    @Override
    public void onData(MoviesResponse response) {

        if (dataView != null) {
            dataView.loadData(response);
            dataView.hideProgress();
        }
    }

    @Override
    public void onTvData(TvResponse response) {
        if (dataView != null) {
            dataView.loadTvData(response);
            dataView.hideProgress();
        }
    }

    @Override
    public void onNowData(String err) {

        if (dataView != null) {
            dataView.onNoData(err);
            dataView.hideProgress();
        }

    }
}
