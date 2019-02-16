package com.example.islam.movies.watch_later;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {WatchLaterEntity.class}, version = 4)
abstract class LaterDataBase extends RoomDatabase {
    abstract LaterDao getDao();
}
