package com.example.islam.movies.watch_later;

import android.content.Context;

import java.util.List;

public class LaterPresenterImpl implements LaterPresenter, LaterInteActor.OnLaterFinished {

    private LaterView view;
    private Context context;
    private LaterInteActor inteActor;

    LaterPresenterImpl(Context context, LaterView view) {

        this.context = context;
        this.view = view;
        inteActor = new LaterInterActorImpl(context);
    }

    @Override
    public void onDone(List<WatchLaterEntity> entityList) {

        if (view != null) {
            view.hideProgress();
            view.showLater(entityList);
        }
    }

    @Override
    public void onError() {
        if (view != null)
            view.hideProgress();

    }

    @Override
    public void getFavourites() {

        if (view != null) {

            view.showProgress();
            inteActor.getFavourites(this);
        }
    }

    @Override
    public void destroy() {
        if (view != null)
            view = null;
    }
}
