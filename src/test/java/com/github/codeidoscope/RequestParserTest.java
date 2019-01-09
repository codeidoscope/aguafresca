package com.github.codeidoscope;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestParserTest {

    private RequestParser requestParser;

    @BeforeEach
    void setUp() {
        requestParser = new RequestParser();
    }

    @Test
    void correctlyParsesGETRequestAndReturnsValidRequest() {
        Request parsedRequest = requestParser.parse("GET /path HTTP/1.1\n\r\n");

        assertEquals(parsedRequest.getMethod(), "GET");
        assertEquals(parsedRequest.getPath(), "/path");
        assertEquals(parsedRequest.getProtocol(), "HTTP/1.1");

    }

    @Test
    void correctlyParsesHEADRequest() {
        Request parsedRequest = requestParser.parse("HEAD /a_path HTTP/1.0\n\r\n");

        assertEquals(parsedRequest.getMethod(), "HEAD");
        assertEquals(parsedRequest.getPath(), "/a_path");
        assertEquals(parsedRequest.getProtocol(), "HTTP/1.0");
    }

    @Test
    void constructsAPathFromAFilePathAndDirectoryPath() {
        String directoryPath = System.getProperty("user.dir");
        String filePath = "/foo.text";
        String expectedConstructedPath = directoryPath + filePath;
        String constructedPath = requestParser.constructFilePath(filePath);

        assertEquals(constructedPath, expectedConstructedPath);
    }
}