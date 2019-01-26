package com.github.codeidoscope;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class HeaderGenerator {
    private static final String PROTOCOL = "HTTP/1.1";
    private static final String ACCEPT_RANGES = "bytes";
    private final String getDateTimeNow;

    HeaderGenerator() {
        this.getDateTimeNow = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());
    }

    HeaderGenerator(String getDateTimeNow) {
        this.getDateTimeNow = getDateTimeNow;
    }

    Header generate(String statusCode, String type, int length) {
        String header = PROTOCOL + " " + statusCode + "\n"
                + "Date: " + getDateTimeNow + "\n"
                + "MIME-Type: " + type + "\n"
                + "Content-Length: " + length + "\n"
                + "Accept-Ranges: " + ACCEPT_RANGES;

        return new Header(header);
    }
}
