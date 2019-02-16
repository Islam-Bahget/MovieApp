package com.example.islam.movies.model;

import com.google.gson.annotations.SerializedName;

public class Crew {
    @SerializedName("department")
    private String department;
    @SerializedName("name")
    private String name;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
