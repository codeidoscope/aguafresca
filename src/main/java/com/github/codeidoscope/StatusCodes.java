package com.github.codeidoscope;

class StatusCodes {
    enum Status {
        OK("200 OK"),
        NOT_FOUND("404 Not Found");

        final String message;

        Status(String message) {
            this.message = message;
        }
    }
}
