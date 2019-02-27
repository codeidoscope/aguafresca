package com.github.codeidoscope.response;

import java.io.IOException;
import java.io.InputStream;

public class Response {
    private Header headers;
    private Body body;

    public Response(Header headers, Body body) {
        this.headers = headers;
        this.body = body;
    }

    byte[] getHeadersToBytes() {
        return headers.toBytes();
    }

    public String getHeadersToString() {
        return headers.getHeaderString();
    }

    public InputStream getBody() {
        return body.getBody();
    }

    public String getBodyToString() throws IOException {
        return body.getBodyString();
    }
}
