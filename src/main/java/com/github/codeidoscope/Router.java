package com.github.codeidoscope;

public interface Router {
    Response route(Request request);

    void setHandlerForRoute(Route route, RouteHandler handler);
}
