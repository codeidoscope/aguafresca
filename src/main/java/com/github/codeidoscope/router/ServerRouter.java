package com.github.codeidoscope.router;

import com.github.codeidoscope.Configuration;
import com.github.codeidoscope.handlers.RouteHandler;
import com.github.codeidoscope.handlers.DefaultHandler;
import com.github.codeidoscope.handlers.DirectoryHandler;
import com.github.codeidoscope.handlers.FileHandler;
import com.github.codeidoscope.handlers.FormDataHandler;
import com.github.codeidoscope.handlers.FormHandler;
import com.github.codeidoscope.request.Request;
import com.github.codeidoscope.response.Response;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerRouter implements Router {
    private Map<Route, RouteHandler> routes = new HashMap<>();

    @Override
    public Response route(Request request) throws IOException {
        File file = new File(Configuration.getInstance().getContentRootPath() + request.getPath());
        Route route = new Route(request.getPath(), request.getMethod());

        setHandlerForRoute(new Route("/form", "GET"), new FormHandler());
        setHandlerForRoute(new Route("/form_results", "POST"), new FormDataHandler());

        if (routes.get(route) != null) {
            return routes.get(route).respondToRequest(request);
        } else if (file.isFile()) {
            return new FileHandler().respondToRequest(request);
        } else if (file.isDirectory()) {
            return new DirectoryHandler().respondToRequest(request);
        } else {
            return new DefaultHandler().respondToRequest(request);
        }
    }

    private void setHandlerForRoute(Route route, RouteHandler handler) {
        routes.put(route, handler);
    }
}