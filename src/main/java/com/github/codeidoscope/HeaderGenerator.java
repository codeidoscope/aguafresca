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

    Header generate(String statusCode, String type, int length, Boolean shouldBeAttachment) {
        String header = PROTOCOL + " " + statusCode + "\r\n"
                + "Date: " + getDateTimeNow + "\r\n"
                + "Content-Type: " + type + "\r\n"
                + "Content-Length: " + length + "\r\n"
                + generateContentDisposition(shouldBeAttachment);

        return new Header(header);
    }

    String generateContentDisposition(Boolean shouldBeAttachment) {
        if (shouldBeAttachment) {
            return "Content-Disposition: attachment\r\n";
        } else {
            return "";
        }
    }
}
