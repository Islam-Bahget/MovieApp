package com.example.islam.movies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TvDetailsResponse {
    @SerializedName("backdrop_path")
    private String backDopPath;
    @SerializedName("first_air_date")
    private String firstAirDate;
    @SerializedName("genres")
    private ArrayList<Genre> genres;
    @SerializedName("name")
    private String name;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("overview")
    private String overView;
    @SerializedName("credits")
    private Credits credits;
    @SerializedName("seasons")
    private ArrayList<TvSeason> seasons;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("vote_count")
    private Integer voteCount;

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public String getBackDopPath() {
        return backDopPath;
    }

    public void setBackDopPath(String backDopPath) {
        this.backDopPath = backDopPath;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public Credits getCredits() {
        return credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }

    public ArrayList<TvSeason> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<TvSeason> seasons) {
        this.seasons = seasons;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }
}
