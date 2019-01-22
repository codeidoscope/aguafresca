package com.github.codeidoscope;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class DefaultHandler implements RouteHandler {
    @Override
    public Response respondToRequest(Request request) {
        byte[] body = "404 - NOT FOUND".getBytes();

        String statusCode = "404 Not Found";
        String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());
        String contentLength = "" + body.length;
        String contentType = "text/plain";
        String headers = request.getProtocol() + " " + statusCode + "\n" + "Date: " + date + "\n" + "Content-Type: " + contentType + "\n" + "Content-Length: " + contentLength;

        return new Response(headers, body);
    }
}
