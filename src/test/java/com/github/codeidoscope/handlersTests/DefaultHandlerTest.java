package com.github.codeidoscope.handlersTests;

import com.github.codeidoscope.handlers.DefaultHandler;
import com.github.codeidoscope.handlers.RouteHandler;
import com.github.codeidoscope.request.Request;
import com.github.codeidoscope.response.Body;
import com.github.codeidoscope.response.Header;
import com.github.codeidoscope.response.Response;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DefaultHandlerTest {

    @Test
    void returnsAnOkResponseWithTheCorrectBodyWhenRequestingATextFile() throws IOException {
        RouteHandler defaultHandler = new DefaultHandler();
        Request request = new Request();
        request.setMethod("GET");
        request.setPath("doesnotexist.txt");
        request.setProtocol("HTTP/1.1");

        Header headers = new Header("HTTP/1.1 404 Not Found\nDate: Fri, 11 Jan 2019 10:30:00 GMT\nContent-Type: text/plain\nContent-Length: 15\nAccept-Ranges: bytes");
        Body body = new Body(new ByteArrayInputStream("404 - NOT FOUND".getBytes()));
        Response expectedResponse = new Response(headers, body);

        Response requestResponse = defaultHandler.respondToRequest(request);

        assertFalse(requestResponse.getHeadersToString().isEmpty());
        assertEquals(expectedResponse.getBodyToString(), requestResponse.getBodyToString());
    }

}