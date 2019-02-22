package com.github.codeidoscope;

import java.io.IOException;
import java.io.InputStream;

public class Response {
    private Header headers;
    private Body body;
    private String contentType;

    Response(Header headers, Body body, String contentType) {
        this.headers = headers;
        this.body = body;
        this.contentType = contentType;
    }

    byte[] getHeadersToBytes() {
        return headers.toBytes();
    }

    String getHeadersToString() {
        return headers.getHeaderString();
    }

    String getContentType() {
        return contentType;
    }

    public InputStream getBody() {
        return body.getBody();
    }

    String getBodyToString() throws IOException {
        return body.getBodyString();
    }
}
