package com.example.islam.movies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Credits implements Serializable {
    @SerializedName("cast")
    @Expose
    private ArrayList<Cast> cast;

    @SerializedName("crew")
    @Expose
    private ArrayList<Crew> crews;

    public ArrayList<Crew> getCrews() {
        return crews;
    }

    public void setCrews(ArrayList<Crew> crews) {
        this.crews = crews;
    }

    public ArrayList<Cast> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Cast> cast) {
        this.cast = cast;
    }
}
