package com.github.codeidoscope;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

class ServerRouter implements Router {
    private Map<Route, RouteHandler> routes = new HashMap<>();

    @Override
    public Response route(Request request) {
        File file = new File(Configuration.getInstance().getContentRootPath() + request.getPath());
        Route route = new Route(request.getPath(), request.getMethod());
        if (routes.get(route) != null) {
            return routes.get(route).respondToRequest(request);
        } else if (file.isFile()){
            return new TextFileHandler().respondToRequest(request);
        } else if (file.isDirectory()){
            return new DirectoryHandler().respondToRequest(request);
        } else {
            return new DefaultHandler().respondToRequest(request);
        }
    }

    @Override
    public void setHandlerForRoute(Route route, RouteHandler handler) {
        routes.put(route, handler);
    }
}