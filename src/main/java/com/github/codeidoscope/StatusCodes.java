package com.github.codeidoscope;

public enum StatusCodes {
    OK("200 OK"),
    NOT_FOUND("404 Not Found");

    public final String message;

    StatusCodes(String message) {
        this.message = message;
    }
}