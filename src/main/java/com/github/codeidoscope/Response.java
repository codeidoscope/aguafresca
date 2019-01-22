package com.github.codeidoscope;

import java.util.Arrays;

public class Response {
    private String headers;
    private byte[] body;

    Response(String headers, byte[] body) {
        this.headers = headers;
        this.body = body;
    }

    String getHeaders() {
        return headers;
    }

    byte[] getBody() {
        return body;
    }

    String getBodyString() {
        return Arrays.toString(body);
    }
}
