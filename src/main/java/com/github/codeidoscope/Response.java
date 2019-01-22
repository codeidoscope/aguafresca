package com.github.codeidoscope;

public class Response {
    private Header headers;
    private Body body;

    Response(Header headers, Body body) {
        this.headers = headers;
        this.body = body;
    }

    byte[] getHeaders() {
        return headers.toBytes();
    }

    byte[] getBody() {
        return body.toBytes();
    }

    String getBodyString() {
        return body.getString();
    }

    String getHeadersString() {
        return headers.getHeaderString();
    }
}
