package com.github.codeidoscope;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class DefaultHandler implements RouteHandler {

    @Override
    public Response respondToRequest(Request request) {
        Body body = new Body("404 - NOT FOUND");
        int bodyLength = body.getLength();

        return new Response(generateHeader(bodyLength), body);
    }

    private Header generateHeader(int bodyLength) {
        String getDateTimeNow = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());

        return new Header("HTTP/1.1 " + StatusCodes.Status.NOT_FOUND.message + "\r\n"
                + "Date: " + getDateTimeNow + "\r\n"
                + "Content-Type: text/plain\r\n"
                + "Content-Length: " + bodyLength + "\r\n"
                + "Accept-Ranges: bytes");
    }
}
