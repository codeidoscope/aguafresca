package com.github.codeidoscope;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ServerRouterTest {

    @BeforeEach
    void setUp() throws IOException {
        Configuration.getInstance().setPortNumber(8080);
        Configuration.getInstance().setContentRootPath(System.getProperty("user.dir"));
    }

    @Test
    void returnsTheCorrectResponseGivenARequest() throws IOException {
        Request request = new Request();
        request.setMethod("GET");
        request.setPath("/public/foo.txt");
        request.setProtocol("HTTP/1.1");

        ServerRouter serverRouter = new ServerRouter();
        Route fooTextFileRoute = new Route("/public/foo.txt", "GET");
        RouteHandler fileHandler = new FileHandler();
        serverRouter.setHandlerForRoute(fooTextFileRoute, fileHandler);

        Header headers = new Header("HTTP/1.1 200 OK\nDate: Fri, 11 Jan 2019 10:30:00 GMT\nContent-Type: text/plain\nContent-Length: 50\nAccept-Ranges: bytes");

        Response expectedResponse = new Response(headers, new Body("This file has some text in it. Isn't that great?!\n"));

//        assertEquals(expectedResponse.getHeadersToString(), serverRouter.route(request).getHeadersToString());
        assertEquals(expectedResponse.getBodyToString(), serverRouter.route(request).getBodyToString());

    }
}