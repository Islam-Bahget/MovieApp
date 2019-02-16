package com.example.islam.movies.data_repository;

import com.example.islam.movies.model.Movie;
import com.example.islam.movies.model.MovieDetailsResponse;
import com.example.islam.movies.model.MoviesResponse;
import com.example.islam.movies.model.TvDetailsResponse;
import com.example.islam.movies.model.TvResponse;
import com.example.islam.movies.model.VideoResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {


    @GET("movie/{type}")
    Single<MoviesResponse> getMovieList(@Path("type") String type, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{type}")
    Flowable<List<Movie>> getMovieListFlow(@Path("type") String type, @Query("api_key") String apiKey, @Query("page") int page);


    @GET("movie/{id}")
    Single<MovieDetailsResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey, @Query("append_to_response") String credits);

    @GET("movie/{id}/videos")
    Single<VideoResponse> getTrailers(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("search/movie/")
    Single<MoviesResponse> searchMovie(@Query("api_key") String apiKey, @Query("query") String query);

    @GET("trending/all/{time_window}")
    Single<MoviesResponse> getTrending(@Path("time_window") String timeWindow, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/{type}")
    Single<TvResponse> getTvList(@Path("type") String type, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/{id}")
    Single<TvDetailsResponse> getTvDetails(@Path("id") int id, @Query("api_key") String apiKey, @Query("append_to_response") String credits);

    @GET("search/tv/")
    Single<TvResponse> searchTv(@Query("api_key") String apiKey, @Query("query") String query);

    @GET("tv/{id}/videos")
    Single<VideoResponse> getTvTrailers(@Path("id") int id, @Query("api_key") String apiKey);

}
