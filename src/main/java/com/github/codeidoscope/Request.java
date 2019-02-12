package com.github.codeidoscope;

import java.util.LinkedHashMap;

public class Request {
    private String method;
    private String path;
    private String protocol;
    private LinkedHashMap<String, String> headers;
    private String body;

    String getMethod() {
        return method;
    }

    String getPath() {
        return path;
    }

    String getProtocol() {
        return protocol;
    }

    String getBody() {
        return body;
    }

    LinkedHashMap<String, String> getHeaders() {
        return headers;
    }

    void setMethod(String method) {
        this.method = method;
    }

    void setPath(String path) {
        this.path = path;
    }

    void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    void setHeaders(LinkedHashMap<String, String> headers) {
        this.headers = headers;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
