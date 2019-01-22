package com.github.codeidoscope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class TextFileHandler implements RouteHandler {
    @Override
    public Response respondToRequest(Request request) throws IOException {
        String contentRootPath = Configuration.getInstance().getContentRootPath();
        String filePath = contentRootPath + request.getPath();

        Body body = new Body(Files.readAllBytes(Paths.get(filePath)));

        String statusCode = "200 OK";
        String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());
        String contentLength = "" + body.getLength();
        String contentType = "text/plain";
        String acceptedRange = "bytes";
        String headersString = request.getProtocol() + " " + statusCode + "\n" + "Date: " + date + "\n" + "Content-Type: " + contentType + "\n" + "Content-Length: " + contentLength + "Accept-Ranges: " + acceptedRange;
        Header headers = new Header(headersString.getBytes());

        return new Response(headers, body);
    }
}
