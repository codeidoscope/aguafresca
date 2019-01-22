package com.github.codeidoscope;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class DefaultHandler implements RouteHandler {
    @Override
    public Response respondToRequest(Request request) {
        Body body = new Body("404 - NOT FOUND".getBytes());

        String statusCode = "404 Not Found";
        String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());
        String contentLength = "" + body.getLength();
        String contentType = "text/plain";
        String headerString = request.getProtocol() + " " + statusCode + "\n" + "Date: " + date + "\n" + "Content-Type: " + contentType + "\n" + "Content-Length: " + contentLength;
        Header headers = new Header(headerString.getBytes());

        return new Response(headers, body);
    }
}
