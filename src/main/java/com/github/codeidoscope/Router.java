package com.github.codeidoscope;

import java.util.HashMap;

class Router {
    private HashMap<Route, RouteHandler> routes = new HashMap<>();

    Response route(Request request) {
        Response response = null;
        for(Route route : routes.keySet()) {
            if(route.getPath().equals(request.getPath())){
                response = routes.get(route).respondToRequest(request);
            }
        }
        return response;
    }

    void setHandlerForRoute(Route route, RouteHandler handler) {
        routes.put(route, handler);
    }
}