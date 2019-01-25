package com.github.codeidoscope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class MockRouteHandler implements RouteHandler {
    @Override
    public Response respondToRequest(Request request) throws IOException {
        String filePath = Configuration.getInstance().getContentRootPath() + "/public" + request.getPath();

        Body body = new Body(Files.readAllBytes(Paths.get(filePath)));

        String statusCode = "200 OK";
        String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.parse("2019-01-11T10:30:00Z[Europe/London]"));
        String contentLength = "" + body.getLength();
        String contentType = "text/plain";
        String headersString = request.getProtocol() + " " + statusCode + "\n" + "Date: " + date + "\n" + "Content-Type: " + contentType + "\n" + "Content-Length: " + contentLength;
        Header headers = new Header(headersString);

        return new Response(headers, body);
    }
}
