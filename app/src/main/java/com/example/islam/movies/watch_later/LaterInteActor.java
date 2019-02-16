package com.example.islam.movies.watch_later;

import java.util.List;

public interface LaterInteActor {
    interface OnLaterFinished {
        void onDone(List<WatchLaterEntity> entityList);

        void onError();
    }

    void getFavourites(OnLaterFinished finished);
}
