package com.github.codeidoscope;

class MockRouterHandler implements RouteHandler {
    @Override
    public Response respondToRequest(Request request) {
        return new Response("HTTP/1.1 200 OK", "Hello World");
    }
}
