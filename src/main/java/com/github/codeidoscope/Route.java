package com.github.codeidoscope;

public class Route {
    private String path;
    private String method;

    Route(String path, String method) {
        this.path = path;
        this.method = method;
    }

    String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }
}
