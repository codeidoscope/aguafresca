package com.github.codeidoscope;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DefaultHandlerTest {

    @Test
    void returnsAResponseWithTheCorrectBody() throws IOException {
        RouteHandler defaultHandler = new DefaultHandler();
        Request request = new Request();
        request.setPath("doesnotexist.txt");
        request.setMethod("GET");
        request.setProtocol("HTTP/1.1");

        Header headers = new Header("HTTP/1.1 404 Not Found\nDate: Fri, 11 Jan 2019 10:30:00 GMT\nContent-Type: text/plain\nContent-Length: 15");
        Body body = new Body("404 - NOT FOUND");

        Response expectedResponse = new Response(headers, body);
        Response requestResponse = defaultHandler.respondToRequest(request);

        assertFalse(requestResponse.getHeadersToString().isEmpty());
        assertEquals(expectedResponse.getBodyToString(), requestResponse.getBodyToString());
    }

}