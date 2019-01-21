package com.github.codeidoscope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class MockRouteHandler implements RouteHandler {
    @Override
    public Response respondToRequest(Request request) throws IOException {

        String filePath = Configuration.getInstance().getContentRootPath() + "/public" + request.getPath();
        String body = "";

        try {
            body = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String statusCode = "200 OK";
        String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.parse("2019-01-11T10:30:00Z[Europe/London]"));
        String contentLength = "" + body.getBytes(StandardCharsets.UTF_8).length;
        String contentType = "text/plain";
        String headers = request.getProtocol() + " " + statusCode + "\n" + "Date: " + date + "\n" + "Content-Type: " + contentType + "\n" + "Content-Length: " + contentLength;

        return new Response(headers, body);
    }
}
