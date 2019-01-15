package com.github.codeidoscope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class TextFileHandler implements RouteHandler {
    @Override
    public Response respondToRequest(Request request) {
        String directoryPath = Configuration.getInstance().getDirectoryPath();
        String subPath = Configuration.getInstance().getSubPath();
        String filePath = directoryPath + subPath + request.getPath();

        String body = "";

        try {
            body = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String statusCode = "200 OK";
        String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());
        String contentLength = "" + body.getBytes(StandardCharsets.UTF_8).length;
        String contentType = "text/plain";
        String headers = request.getProtocol() + " " + statusCode + "\n" + "Date: " + date + "\n" + "Content-Type: " + contentType + "\n" + "Content-Length: " + contentLength;

        return new Response(headers, body);
    }
}
