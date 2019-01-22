package com.github.codeidoscope;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HeaderGeneratorTest {

    @Test
    void aNewHeaderObjectWithCorrectDataIsCreated() {
        HeaderGenerator headerGenerator = new MockHeaderGenerator();
        String statusCode = "200 OK";
        String type = "text/plain";
        int length = 2547;

        String headerString = "HTTP/1.1 200 OK\nDate: Fri, 11 Jan 2019 10:30:00 GMT\nMIME type: text/plain\nContent-Length: 2547\nAccept-Ranges: bytes";
        Header expectedHeader = new Header(headerString.getBytes());

        Header generatedHeader = headerGenerator.generate(statusCode, type, length);

        assertEquals(expectedHeader.getHeaderString(), generatedHeader.getHeaderString());
    }
}
