package com.github.codeidoscope;

public class Response {
    private String headers;
    private String body;

    Response(String headers, String body) {
        this.headers = headers;
        this.body = body;
    }

    String getHeaders() {
        return headers;
    }

    String getBody() {
        return body;
    }
}
