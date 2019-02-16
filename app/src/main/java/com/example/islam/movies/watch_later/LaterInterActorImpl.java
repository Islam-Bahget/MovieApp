package com.example.islam.movies.watch_later;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LaterInterActorImpl implements LaterInteActor {

    private CompositeDisposable disposable;
    private Context context;

    public LaterInterActorImpl(Context context) {

        this.context = context;
        disposable = new CompositeDisposable();
    }

    @Override
    public void getFavourites(OnLaterFinished finished) {

        LaterDao dao = DataBaseSingleton.laterDao(context);
//        dao.getliveAll().observeForever(finished::onDone);
//
        disposable.add(dao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(finished::onDone));

    }
}
