package com.example.islam.movies.data_repository;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ErrorInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Response response = chain.proceed(chain.request());
        if (!response.isSuccessful()) {
            throw new MoviesException(response.code(), response.message());


        }

        return chain.proceed(chain.request());
    }
}
