package com.github.codeidoscope;

import java.util.HashMap;
import java.util.Map;

class Router {
    private Map<Route, RouteHandler> routes = new HashMap<>();

    Response route(Request request) {
        Route route = new Route(request.getPath(), request.getMethod());
        return routes.get(route).respondToRequest(request);
    }

    void setHandlerForRoute(Route route, RouteHandler handler) {
        routes.put(route, handler);
    }
}