package com.github.codeidoscope;

import java.util.HashMap;
import java.util.Map;

class ServerRouter implements Router {
    private Map<Route, RouteHandler> routes = new HashMap<>();

    @Override
    public Response route(Request request) {
        Route route = new Route(request.getPath(), request.getMethod());
        return routes.get(route).respondToRequest(request);
    }

    @Override
    public void setHandlerForRoute(Route route, RouteHandler handler) {
        routes.put(route, handler);
    }
}