package com.github.codeidoscope.handlers;

import com.github.codeidoscope.response.Body;
import com.github.codeidoscope.response.Header;
import com.github.codeidoscope.request.Request;
import com.github.codeidoscope.response.Response;
import com.github.codeidoscope.response.ResponseBuilder;
import com.github.codeidoscope.StatusCodes;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DefaultHandler implements RouteHandler {

    @Override
    public Response respondToRequest(Request request) {
        String content = "404 - NOT FOUND";
        int bodyLength = content.length();

        InputStream generatedContent = new ByteArrayInputStream(content.getBytes());
        Body body = new Body(generatedContent);

        return new Response(generateHeader(bodyLength), body);

    }

    private Header generateHeader(int bodyLength) {
        String getDateTimeNow = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());

        ResponseBuilder responseBuilder = new ResponseBuilder(StatusCodes.Status.NOT_FOUND)
                .addHeader("Date", getDateTimeNow)
                .addHeader("Content-Type", "text/plain")
                .addHeader("Content-Length", String.valueOf(bodyLength));

        return new Header(responseBuilder.setHeader());
    }
}
