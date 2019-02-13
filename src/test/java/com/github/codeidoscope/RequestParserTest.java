package com.github.codeidoscope;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class RequestParserTest {

    private RequestParser requestParser;

    @BeforeEach
    void setUp() {
        requestParser = new RequestParser();
    }

    @Test
    void correctlyParsesGETRequestAndReturnsValidRequest() {
        Request parsedRequest = requestParser.parseMethodPathProtocol("GET /path HTTP/1.1\r\n");

        assertEquals(parsedRequest.getMethod(), "GET");
        assertEquals(parsedRequest.getPath(), "/path");
        assertEquals(parsedRequest.getProtocol(), "HTTP/1.1");

    }

    @Test
    void correctlyParsesHEADRequest() {
        Request parsedRequest = requestParser.parseMethodPathProtocol("HEAD /a_path HTTP/1.0\r\n");

        assertEquals(parsedRequest.getMethod(), "HEAD");
        assertEquals(parsedRequest.getPath(), "/a_path");
        assertEquals(parsedRequest.getProtocol(), "HTTP/1.0");
    }

    @Test
    void returnsParsedRequestWithExpectedAttributes() throws IOException {
        String rawRequest = "GET /testdirectory HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Content-Length: 25\r\n" +
                "Accept-Language: en-GB,en-US;q=0.9,en;q=0.8,fr;q=0.7\r\n" +
                "\r\n" +
                "Test body";

        ByteArrayInputStream mockInputStream = new ByteArrayInputStream(rawRequest.getBytes());
        LinkedHashMap expectedHeaders = new LinkedHashMap<String, String>() {{
            put("Host", "localhost:8080");
            put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            put("Accept-Encoding", "gzip, deflate, br");
            put("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8,fr;q=0.7");
            put("Content-Length", "25");
        }};

        Request request = requestParser.parse(mockInputStream);
        assertEquals("GET", request.getMethod());
        assertEquals("/testdirectory", request.getPath());
        assertEquals("HTTP/1.1", request.getProtocol());
        assertEquals(expectedHeaders, request.getHeaders());
        assertTrue(request.getBody().contains("Test body"));
    }
}