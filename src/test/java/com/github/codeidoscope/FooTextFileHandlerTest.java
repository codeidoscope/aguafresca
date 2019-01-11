package com.github.codeidoscope;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FooTextFileHandlerTest {

    @Test
    void returnsAResponseWithTheCorrectBody() {
        MockRouteHandler mockRouteHandler = new MockRouteHandler();
        Request request = new Request();
        request.setPath("/foo.txt");
        request.setMethod("GET");
        request.setProtocol("HTTP/1.1");

        String headers = "HTTP/1.1 200 OK\n" +
                "Date: Fri, 11 Jan 2019 10:30:00 GMT\n" +
                "Content-Type: text/plain\n" +
                "Content-Length: 50";

        Response expectedResponse = new Response(headers, "This file has some text in it. Isn't that great?!\n");
        Response requestResponse = mockRouteHandler.respondToRequest(request);

        assertEquals(expectedResponse.getHeaders(), requestResponse.getHeaders());
        assertEquals(expectedResponse.getBody(), requestResponse.getBody());
    }
}