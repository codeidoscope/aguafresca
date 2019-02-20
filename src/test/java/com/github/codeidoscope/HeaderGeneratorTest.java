package com.github.codeidoscope;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HeaderGeneratorTest {
    private Boolean shouldBeAttachment = true;

    @Test
    void aNewHeaderObjectWithCorrectDataIsCreatedWhenShouldBeAttachmentIsTrue() {
        String dateTime = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.parse("2019-01-11T10:30:00Z[Europe/London]"));
        HeaderGenerator headerGenerator = new HeaderGenerator(dateTime);
        String type = "text/plain";
        int length = 1234;
        String headerString = "HTTP/1.1 200 OK\r\n" +
                "Date: Fri, 11 Jan 2019 10:30:00 GMT\r\n" +
                "Content-Type: text/plain\r\n" +
                "Content-Length: 1234\r\n" +
                "Content-Disposition: attachment\r\n";
        Header expectedHeader = new Header(headerString);
        Header generatedHeader = headerGenerator.generate(StatusCodes.Status.OK.message, type, length, shouldBeAttachment);

        assertEquals(expectedHeader.getHeaderString(), generatedHeader.getHeaderString());
    }

    @Test
    void aNewHeaderObjectWithCorrectDataIsCreatedWhenShouldBeAttachmentIsFalse() {
        String dateTime = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.parse("2019-01-11T10:30:00Z[Europe/London]"));
        HeaderGenerator headerGenerator = new HeaderGenerator(dateTime);
        String type = "text/plain";
        int length = 1234;
        String headerString = "HTTP/1.1 200 OK\r\n" +
                "Date: Fri, 11 Jan 2019 10:30:00 GMT\r\n" +
                "Content-Type: text/plain\r\n" +
                "Content-Length: 1234\r\n";
        Header expectedHeader = new Header(headerString);
        Header generatedHeader = headerGenerator.generate(StatusCodes.Status.OK.message, type, length, false);

        assertEquals(expectedHeader.getHeaderString(), generatedHeader.getHeaderString());
    }

    @Test
    void contentDispositionIsReturnedWhenShouldBeAttachmentIsTrue() {
        String dateTime = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.parse("2019-01-11T10:30:00Z[Europe/London]"));
        HeaderGenerator headerGenerator = new HeaderGenerator(dateTime);
        String contentDisposition = headerGenerator.generateContentDisposition(true);

        assertEquals("Content-Disposition: attachment\r\n", contentDisposition);
    }

    @Test
    void emptyStringIsReturnedWhenShouldBeAttachmentIsFalse() {
        String dateTime = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.parse("2019-01-11T10:30:00Z[Europe/London]"));
        HeaderGenerator headerGenerator = new HeaderGenerator(dateTime);
        String contentDisposition = headerGenerator.generateContentDisposition(false);

        assertEquals("", contentDisposition);
    }
}
