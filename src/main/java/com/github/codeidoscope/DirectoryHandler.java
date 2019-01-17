package com.github.codeidoscope;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DirectoryHandler implements RouteHandler {
    static StringBuilder htmlContent = new StringBuilder();

    @Override
    public Response respondToRequest(Request request) {
        String contentRootPath = Configuration.getInstance().getContentRootPath();
        String filePath = contentRootPath + request.getPath();

        String body = generateBodyFromDirectory(filePath);

        String statusCode = "200 OK";
        String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());
        String contentLength = "" + body.getBytes(StandardCharsets.UTF_8).length;
        String contentType = "text/html";
        String headers = request.getProtocol() + " " + statusCode + "\n" + "Date: " + date + "\n" + "Content-Type: " + contentType + "\n" + "Content-Length: " + contentLength;

        return new Response(headers, body);
    }

    String removeBasePathFromPath(String path){
        return path.replace(Configuration.getInstance().getContentRootPath(), "");
    }

    String createHtmlLink(String filePath) {
        return String.format("<li><a href=\"%s\">%s</a></li>", filePath, removeBasePathFromPath(filePath));
    }

    void addToHtmlContent(String content) {
        htmlContent.append(content);
    }

    String addHtmlContentToBody(String htmlContent) {
        String htmlFileToString = "";
        String htmlTemplatePath = Configuration.getInstance().getContentRootPath() + "/public/directorylisting.html";

        try {
            htmlFileToString = new String(Files.readAllBytes(Paths.get(htmlTemplatePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return htmlFileToString.replace("$body", htmlContent);
    }

    String generateBodyFromDirectory(String path) {
        File[] files = new File(path).listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    String directoryPath = file.getAbsolutePath();
                    directoryPath = removeBasePathFromPath(directoryPath);
                    String link = createHtmlLink(directoryPath);
                    addToHtmlContent(link);
                } else {
                    String filePath = file.getAbsolutePath();
                    filePath = removeBasePathFromPath(filePath);
                    String link = createHtmlLink(filePath);
                    addToHtmlContent(link);
                }
            }
        }
        return addHtmlContentToBody(htmlContent.toString());
    }
}
