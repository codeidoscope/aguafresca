package com.github.codeidoscope;

import java.util.Objects;

public class Route {
    private String path;
    private String method;

    Route(String path, String method) {
        this.path = path;
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(path, route.path) &&
                Objects.equals(method, route.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, method);
    }
}