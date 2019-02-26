package com.github.codeidoscope;

import java.io.IOException;
import java.io.InputStream;

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

    public InputStream getBody() {
        return body.getBody();
    }

    String getBodyToString() throws IOException {
        return body.getBodyString();
    }
}
