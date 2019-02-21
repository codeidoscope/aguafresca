package com.github.codeidoscope;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class DefaultHandler implements RouteHandler {

    @Override
    public Response respondToRequest(Request request) {
        String content = "404 - NOT FOUND";
        int bodyLength = content.length();
        String contentType = "text/plain";

        InputStream generatedContent = new ByteArrayInputStream(content.getBytes());
        Body body = new Body(generatedContent);

        return new Response(generateHeader(bodyLength), body, contentType);

    }

    private Header generateHeader(int bodyLength) {
        String getDateTimeNow = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());

        ResponseBuilder responseBuilder = new ResponseBuilder(StatusCodes.Status.OK)
                .addHeader("Date", getDateTimeNow)
                .addHeader("Content-Type", "text/plain")
                .addHeader("Content-Length", String.valueOf(bodyLength));

        return new Header(responseBuilder.setHeader());
    }
}
