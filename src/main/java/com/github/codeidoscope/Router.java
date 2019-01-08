package com.github.codeidoscope;

class Router {

    String route(Request request) {
        switch(request.getPath()) {
            case "/valid":
                return "HTTP/1.1 200 OK\n\r\nHello World";
            case "/alsovalid":
                return "HTTP/1.1 200 OK\r\n";
            default:
                return "HTTP/1.1 404 Not Found\r\n";
        }
    }
}