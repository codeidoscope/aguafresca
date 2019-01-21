package com.github.codeidoscope;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DirectoryHandler implements RouteHandler {
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

    String removeBasePathFromPath(String path) {
        Path absolutePath = Paths.get(path);
        Path relativePath = Paths.get(Configuration.getInstance().getContentRootPath()).relativize(absolutePath);
        return relativePath.toString();
    }

    String createHtmlLink(String filePath) {
        return String.format("<li><a href=\"/%s\">%s</a></li>", filePath, filePath);
    }

    void addToHtmlContent(String content, StringBuilder htmlContent) {
        htmlContent.append(content);
    }

    String addHtmlContentToBody(StringBuilder htmlContent) {
        String htmlTemplatePath = "<!DOCTYPE html>\n<head>\n</head>\n<body>\n$body\n</body>\n</html>\n";
        return htmlTemplatePath.replace("$body", htmlContent);
    }

    String generateBodyFromDirectory(String path) {
        StringBuilder htmlContent = new StringBuilder();
        File[] files = new File(path).listFiles();

        if (files != null) {
            for (File file : files) {
                String filePath = null;
                try {
                    filePath = file.getCanonicalPath();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                filePath = removeBasePathFromPath(filePath);
                String link = createHtmlLink(filePath);
                addToHtmlContent(link, htmlContent);
            }
        }
        return addHtmlContentToBody(htmlContent);
    }
}
