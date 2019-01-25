package com.github.codeidoscope;

public class Response {
    private Header headers;
    private Body body;

    Response(Header headers, Body body) {
        this.headers = headers;
        this.body = body;
    }

    byte[] getHeadersToBytes() {
        return headers.toBytes();
    }

    String getHeadersToString() {
        return headers.getHeaderString();
    }

    byte[] getBodyToBytes() {
        return body.toBytes();
    }

    String getBodyToString() {
        return body.getBodyString();
    }
}
