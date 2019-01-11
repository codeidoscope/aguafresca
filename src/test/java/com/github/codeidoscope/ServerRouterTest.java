package com.github.codeidoscope;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerRouterTest {

    @Test
    void returnsTheCorrectResponseGivenARequest() {
        Request request = new Request();
        request.setMethod("GET");
        request.setPath("/foo.txt");
        request.setProtocol("HTTP/1.1");

        ServerRouter serverRouter = new ServerRouter();
        Route fooTextFileRoute = new Route("/foo.txt", "GET");
        RouteHandler mockRouteHandler = new MockRouteHandler();
        serverRouter.setHandlerForRoute(fooTextFileRoute, mockRouteHandler);

        String headers = "HTTP/1.1 200 OK\n" +
                "Date: Fri, 11 Jan 2019 10:30:00 GMT\n" +
                "Content-Type: text/plain\n" +
                "Content-Length: 50";

        Response expectedResponse = new Response(headers, "This file has some text in it. Isn't that great?!\n");

        assertEquals(serverRouter.route(request).getHeaders(), expectedResponse.getHeaders());
        assertEquals(serverRouter.route(request).getBody(), expectedResponse.getBody());

    }
}