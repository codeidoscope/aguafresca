package com.github.codeidoscope.routerTests;

import com.github.codeidoscope.request.Request;
import com.github.codeidoscope.response.Response;
import com.github.codeidoscope.router.Router;

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
