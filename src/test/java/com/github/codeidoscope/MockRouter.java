package com.github.codeidoscope;

import java.util.HashMap;
import java.util.Map;

public class MockRouter implements Router {
    private Map<String, Response> routes = new HashMap<>();

    @Override
    public Response route(Request request) {
        return routes.get(request.getPath());
    }

    void addRoute(String path, Response response) {
        routes.put(path, response);
    }
}
