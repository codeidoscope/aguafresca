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

        ResponseBuilder responseBuilder = new ResponseBuilder(StatusCodes.Status.OK)
                .addHeader("Date", getDateTimeNow)
                .addHeader("Content-Type", "text/plain")
                .addHeader("Content-Length", String.valueOf(bodyLength))
                .addHeader("Accept-Ranges", "bytes");

        return new Header(responseBuilder.setHeader());
    }
}
