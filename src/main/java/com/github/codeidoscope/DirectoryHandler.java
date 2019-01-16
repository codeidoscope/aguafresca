package com.github.codeidoscope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DirectoryHandler implements RouteHandler {
    static StringBuilder htmlContent = new StringBuilder();

    @Override
    public Response respondToRequest(Request request) {
        return null;
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
        return String.format("<a href=\"%s\">%s</a>", filePath, extractPath(filePath));
    }

    void addToHtmlContent(String content) {
        htmlContent.append(content);
    }

    String addToHtmlBody(String htmlContent) {
        String htmlFileToString = "";
        String htmlTemplatePath = Configuration.getInstance().getDirectoryPath() + "/public/directorylisting.html";

        try {
            htmlFileToString = new String(Files.readAllBytes(Paths.get(htmlTemplatePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return htmlFileToString.replace("$body", htmlContent);
    }
}
