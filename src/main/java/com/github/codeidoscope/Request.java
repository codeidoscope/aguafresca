package com.github.codeidoscope;

public class Request {
    private String path;
    private String method;
    private String protocol;

    String getPath() {
        return path;
    }

    String getMethod() {
        return method;
    }

    String getProtocol() {
        return protocol;
    }

    void setPath(String path) {
        this.path = path;
    }

    void setMethod(String method) {
        this.method = method;
    }

    void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
