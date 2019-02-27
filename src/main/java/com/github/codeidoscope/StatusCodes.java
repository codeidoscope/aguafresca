package com.github.codeidoscope;

public class StatusCodes {
    public enum Status {
        OK("200 OK"),
        NOT_FOUND("404 Not Found");

        public final String message;

        Status(String message) {
            this.message = message;
        }
    }
}
