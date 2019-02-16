package com.example.islam.movies.data_repository;

import java.io.IOException;

public class MoviesException extends IOException {
    private int code;
    private String message;

    public MoviesException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
