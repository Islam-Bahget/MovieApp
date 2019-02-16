package com.example.islam.movies.watch_later;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import io.reactivex.annotations.NonNull;

public class DataBaseSingleton {
    private static LaterDao mInstance = null;
    private static final String DATABASE_NAME = "laterdb";

    public static synchronized LaterDao laterDao(Context context) {
        if (mInstance == null) {
            LaterDataBase dataBase = Room.databaseBuilder(context,
                    LaterDataBase.class, DATABASE_NAME)
                    .addMigrations(new Migration(3, 4) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {

                        }
                    })
                    .allowMainThreadQueries().build();

            mInstance = dataBase.getDao();
        }
        return mInstance;
    }
}
