package com.example.islam.movies;

import android.content.Context;

import com.example.islam.movies.watch_later.DataBaseSingleton;
import com.example.islam.movies.watch_later.LaterDao;
import com.example.islam.movies.watch_later.WatchLaterEntity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@RunWith(MockitoJUnitRunner.class)
public class MoviesTest {
    @Test
    public void testMovies() {


        Context context = Mockito.spy(Context.class);

        LaterDao dao = DataBaseSingleton.laterDao(context);

        

        Mockito.verify(dao.getAll()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new FlowableSubscriber<List<WatchLaterEntity>>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(List<WatchLaterEntity> entityList) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
