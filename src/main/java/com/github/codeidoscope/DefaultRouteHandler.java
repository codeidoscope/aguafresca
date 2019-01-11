package com.github.codeidoscope;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class DefaultRouteHandler implements RouteHandler {
    @Override
    public Response respondToRequest(Request request) {
        String body = "404 - NOT FOUND";

        String statusCode = "404 Not Found";
        String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());
        String contentLength = "" + body.getBytes(StandardCharsets.UTF_8).length;
        String contentType = "text/plain";
        String headers = request.getProtocol() + " " + statusCode + "\n"  + "Date: " + date + "\n" + "Content-Type: " + contentType + "\n" + "Content-Length: " + contentLength;

        return new Response(headers, body);
    }
}
