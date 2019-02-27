package com.github.codeidoscope.handlersTests;

import com.github.codeidoscope.Configuration;
import com.github.codeidoscope.handlers.FileHandler;
import com.github.codeidoscope.handlers.RouteHandler;
import com.github.codeidoscope.request.Request;
import com.github.codeidoscope.response.Body;
import com.github.codeidoscope.response.Header;
import com.github.codeidoscope.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    @BeforeEach
    void setUp() throws IOException {
        Configuration.getInstance().setPortNumber(8080);
        Configuration.getInstance().setContentRootPath(System.getProperty("user.dir"));
    }

    @Test
    void returnsAResponseWithTheCorrectBodyForATextFile() throws IOException {
        RouteHandler fileHandler = new FileHandler();
        Request request = new Request();
        request.setMethod("GET");
        request.setPath("/testdirectory/testfile.txt");
        request.setProtocol("HTTP/1.1");

        Header headers = new Header("HTTP/1.1 200 OK\nDate: Fri, 11 Jan 2019 10:30:00 GMT\nContent-Type: text/plain\nContent-Length: 10\nAccept-Ranges: bytes");
        Body body = new Body(new ByteArrayInputStream("Test file.".getBytes()));
        Response expectedResponse = new Response(headers, body);

        Response requestResponse = fileHandler.respondToRequest(request);

        assertFalse(requestResponse.getHeadersToString().isEmpty());
        assertEquals(expectedResponse.getBodyToString(), requestResponse.getBodyToString());
    }
}