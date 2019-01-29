package com.github.codeidoscope;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HeaderGeneratorTest {

    @Test
    void aNewHeaderObjectWithCorrectDataIsCreated() {
        String dateTime = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.parse("2019-01-11T10:30:00Z[Europe/London]"));
        HeaderGenerator headerGenerator = new HeaderGenerator(dateTime);
        String statusCode = "200 OK";
        String type = "text/plain";
        int length = 1234;

        String headerString = "HTTP/1.1 200 OK\nDate: Fri, 11 Jan 2019 10:30:00 GMT\nContent-Type: text/plain\nContent-Length: 1234\nAccept-Ranges: bytes";
        Header expectedHeader = new Header(headerString);

        Header generatedHeader = headerGenerator.generate(statusCode, type, length);

        assertEquals(expectedHeader.getHeaderString(), generatedHeader.getHeaderString());
    }

    @Test
    void contentDispositionIsReturnedIfTheFileIsAPdfLargerThanTenMb() {
        String dateTime = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.parse("2019-01-11T10:30:00Z[Europe/London]"));
        HeaderGenerator headerGenerator = new HeaderGenerator(dateTime);

        String contentDisposition = headerGenerator.generateContentDisposition("application/pdf", 20485760);
        assertEquals("Content-Disposition: attachment\n", contentDisposition);
    }

    @Test
    void contentDispositionIsEmptyIfTheFileIsAPdfSmallerThanTenMb() {
        String dateTime = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.parse("2019-01-11T10:30:00Z[Europe/London]"));
        HeaderGenerator headerGenerator = new HeaderGenerator(dateTime);

        String contentDisposition = headerGenerator.generateContentDisposition("application/pdf", 632806);
        assertEquals("", contentDisposition);
    }

    @Test
    void contentDispositionIsEmptyIfNonPdfFileIsSmallerThanTenMb() {
        String dateTime = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.parse("2019-01-11T10:30:00Z[Europe/London]"));
        HeaderGenerator headerGenerator = new HeaderGenerator(dateTime);

        String contentDisposition = headerGenerator.generateContentDisposition("text/plain", 53);
        assertEquals("", contentDisposition);
    }

    @Test
    void contentDispositionIsEmptyIfNonPdfFileIsLargerThanTenMb() {
        String dateTime = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.parse("2019-01-11T10:30:00Z[Europe/London]"));
        HeaderGenerator headerGenerator = new HeaderGenerator(dateTime);

        String contentDisposition = headerGenerator.generateContentDisposition("text/plain", 2632806);
        assertEquals("", contentDisposition);
    }
}
