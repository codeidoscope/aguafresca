package com.github.codeidoscope;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    @Test
    void returnsAResponseWithTheCorrectBody() throws IOException {
        MockRouteHandler mockRouteHandler = new MockRouteHandler();
        Request request = new Request();
        request.setPath("/foo.txt");
        request.setMethod("GET");
        request.setProtocol("HTTP/1.1");

        Header headers = new Header("HTTP/1.1 200 OK\nDate: Fri, 11 Jan 2019 10:30:00 GMT\nContent-Type: text/plain\nContent-Length: 50");
        Body body = new Body("This file has some text in it. Isn't that great?!\n");

        Response expectedResponse = new Response(headers, body);
        Response requestResponse = mockRouteHandler.respondToRequest(request);

        assertEquals(expectedResponse.getHeadersString(), requestResponse.getHeadersString());
        assertEquals(expectedResponse.getBodyString(), requestResponse.getBodyString());
    }
}