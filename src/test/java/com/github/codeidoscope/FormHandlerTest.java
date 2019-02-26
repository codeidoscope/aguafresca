package com.github.codeidoscope;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FormHandlerTest {

    @Test
    void returnsAResponseWithTheCorrectBodyForAForm() throws IOException {
        FormHandler formHandler = new FormHandler();
        Request request = new Request();
        request.setMethod("GET");
        request.setPath("/form");
        request.setProtocol("HTTP/1.1");

        Header headers = new Header("HTTP/1.1 200 OK\nDate: Fri, 11 Jan 2019 10:30:00 GMT\nContent-Type: text/html\nContent-Length: 845\nAccept-Ranges: bytes");
        Body body = new Body(new ByteArrayInputStream(formHandler.generateHtmlForm().getBytes()));
        Response expectedResponse = new Response(headers, body);

        Response requestResponse = formHandler.respondToRequest(request);

        assertFalse(requestResponse.getHeadersToString().isEmpty());
        assertEquals(expectedResponse.getBodyToString(), requestResponse.getBodyToString());
    }
}