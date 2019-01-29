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
                + "Content-Type: " + type + "\n"
                + "Content-Length: " + length + "\n"
                + "Accept-Ranges: " + ACCEPT_RANGES
                + generateContentDisposition(type, length);

        return new Header(header);
    }

    private String generateContentDisposition(String type, int length) {
        int TenMbInBytes = 10485760;
        if (type.equals("application/pdf") && length > TenMbInBytes) {
            return "Content-Disposition: attachment\n";
        } else {
            return "";
        }
    }
}
