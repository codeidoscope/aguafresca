package com.github.codeidoscope.responseTests;

import com.github.codeidoscope.StatusCodes;
import com.github.codeidoscope.response.ResponseBuilder;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class ResponseBuilderTest {
    @Test
    void addResponseHeader() {
        ResponseBuilder responseBuilder = new ResponseBuilder(StatusCodes.OK);
        responseBuilder.addHeader("key", "value");

        assertEquals(responseBuilder.getHeader("key"), "value");
    }

    @Test
    void convertsToString() {
        ResponseBuilder responseBuilder = new ResponseBuilder(StatusCodes.OK);
        responseBuilder.addHeader("key", "value");

        String expectedResponse = "HTTP/1.1 200 OK\r\n" +
                "key: value\r\n";

        assertEquals(expectedResponse, responseBuilder.setHeader());
    }

    @Test
    void addBody() {
        ResponseBuilder responseBuilder = new ResponseBuilder(StatusCodes.OK);
        responseBuilder.setBody("Test body".getBytes());

        assertEquals("Test body", new String(responseBuilder.getBody(), StandardCharsets.UTF_8));
    }
}