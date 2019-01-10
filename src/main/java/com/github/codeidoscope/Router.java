package com.github.codeidoscope;

import java.util.HashMap;

class Router {
    public HashMap<Route, RouteHandler> routes = new HashMap<>();

    Response route(Request request) {
        System.out.println(routes);
        System.out.println(request.getPath());
        System.out.println(routes.get(Route.getPath(request.getPath())));

        return routes.get(request.getPath()).respondToRequest();
//        switch(request.getPath()) {
//            case "/valid":
//                return "HTTP/1.1 200 OK\n\r\nHello World";
//            case "/alsovalid":
//                return "HTTP/1.1 200 OK\r\n";
//            case "/foo.txt":
//                return "HTTP/1.1 200 OK\n\r\n /Users/marion/Desktop/Engineering/aguafresca/public/foo.txt";
////                String fileContents = doSomeWizardry();
////                return String.format("HTTP/1.1 200 OK\nContent-Type: text/plain\n\r\n%s", fileContents);
//            default:
//                return "HTTP/1.1 404 Not Found\r\n";
//        }
    }

    void setHandlerForRoute(Route route, RouteHandler handler) {
        routes.put(route, handler);
    }

//    Route createRoute(String method, String path){
//        return new Route(method, path);
//    }
}