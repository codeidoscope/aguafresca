package com.github.codeidoscope;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class MockHeaderGenerator implements HeaderGenerator {
    private static final String PROTOCOL = "HTTP/1.1";
    private static final String ACCEPT_RANGES = "bytes";
    private final String DATE = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.parse("2019-01-11T10:30:00Z[Europe/London]"));

    @Override
    public Header generate(String statusCode, String type, int length) {
        String header = PROTOCOL + " " + statusCode + "\n"
                + "Date: " + DATE + "\n"
                + "MIME type: " + type + "\n"
                + "Content-Length: " + length + "\n"
                + "Accept-Ranges: " + ACCEPT_RANGES;

        return new Header(header.getBytes());
    }
}
