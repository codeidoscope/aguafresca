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
        String directoryPath = Configuration.getInstance().getDirectoryPath();
        String subPath = Configuration.getInstance().getSubPath();
        String filePath = directoryPath + subPath;

        String body = generateBodyFromDirectory(filePath);

        String statusCode = "200 OK";
        String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());
        String contentLength = "" + body.getBytes(StandardCharsets.UTF_8).length;
        String contentType = "text/html";
        String headers = request.getProtocol() + " " + statusCode + "\n" + "Date: " + date + "\n" + "Content-Type: " + contentType + "\n" + "Content-Length: " + contentLength;

        return new Response(headers, body);
    }

    String extractPath(String path) {
        if (!Configuration.getInstance().getSubPath().equals("")) {
            path = path.replace(Configuration.getInstance().getSubPath(), "");
        }
        path = path.replace(Configuration.getInstance().getDirectoryPath(), "");

        return path;
    }

    String extractDirectoryPath(String path) {
        if (!Configuration.getInstance().getSubPath().equals("")) {
            path = path.replace(Configuration.getInstance().getDirectoryPath(), "");
        } else {
            path = path.replace(Configuration.getInstance().getDirectoryPath(), "") + "/";
        }

        return path;
    }

    String createHtmlLink(String filePath) {
        return String.format("<li><a href=\"%s\">%s</a></li>", filePath, extractPath(filePath));
    }

    void addToHtmlContent(String content) {
        htmlContent.append(content);
    }

    String addHtmlContentToBody(String htmlContent) {
        String htmlFileToString = "";
        String htmlTemplatePath = Configuration.getInstance().getDirectoryPath() + "/public/directorylisting.html";

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
                    directoryPath = extractDirectoryPath(directoryPath);
                    String link = createHtmlLink(directoryPath);
                    addToHtmlContent(link);
                } else {
                    String filePath = file.getAbsolutePath();
                    filePath = extractPath(filePath);
                    String link = createHtmlLink(filePath);
                    addToHtmlContent(link);
                }
            }
        }
        return addHtmlContentToBody(htmlContent.toString());
    }
}
