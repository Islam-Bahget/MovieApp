package com.example.islam.movies.watch_later;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface LaterDao {
    @Insert
    void insertLater(WatchLaterEntity entity);


    @Delete
    void deleteLater(WatchLaterEntity entity);

    @Delete
    void deleteLaterAll(List<WatchLaterEntity> list);

    @Query("select * from later")
    Flowable<List<WatchLaterEntity>> getAll();

    @Query("select * from later where item_id=:id")
    Single<WatchLaterEntity> getLater(int id);
}
