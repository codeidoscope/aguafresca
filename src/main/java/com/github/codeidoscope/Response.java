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

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
