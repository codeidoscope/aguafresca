package com.github.codeidoscope;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouterTest {

    @Test
    void returnsTheCorrectResponseGivenARequest() {
        Request request = new Request();
        request.setMethod("GET");
        request.setPath("/foo.txt");
        request.setProtocol("HTTP/1.1");

        Router router = new Router();
        Route fooTextFileRoute = new Route("/foo.txt", "GET");
        RouteHandler mockRouteHandler = new MockRouterHandler();
        router.setHandlerForRoute(fooTextFileRoute, mockRouteHandler);

        Response expectedResponse = new Response("HTTP/1.1 200 OK", "Hello World");

        assertEquals(router.route(request), expectedResponse);
    }
}