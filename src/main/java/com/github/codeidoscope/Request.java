package com.github.codeidoscope;

public class Request {
    private String method;
    private String path;
    private String protocol;

    String getMethod() {
        return method;
    }

    String getPath() {
        return path;
    }

    String getProtocol() {
        return protocol;
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
}
