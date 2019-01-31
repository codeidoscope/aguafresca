package com.github.codeidoscope;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    @Test
    void returnsAResponseWithTheCorrectBodyForATextFile() throws IOException {
        RouteHandler fileHandler = new FileHandler();
        Request request = new Request();
        request.setMethod("GET");
        request.setPath("/testdirectory/testfile.txt");
        request.setProtocol("HTTP/1.1");
        Header headers = new Header("HTTP/1.1 200 OK\nDate: Fri, 11 Jan 2019 10:30:00 GMT\nContent-Type: text/plain\nContent-Length: 10\nAccept-Ranges: bytes");
        Body body = new Body("Test file.");
        Response expectedResponse = new Response(headers, body);
        Response requestResponse = fileHandler.respondToRequest(request);

        assertFalse(requestResponse.getHeadersToString().isEmpty());
        assertEquals(expectedResponse.getBodyToString(), requestResponse.getBodyToString());
    }
}