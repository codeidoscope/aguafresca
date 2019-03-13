package com.github.codeidoscope.handlers;

import com.github.codeidoscope.response.Body;
import com.github.codeidoscope.Configuration;
import com.github.codeidoscope.response.Header;
import com.github.codeidoscope.request.Request;
import com.github.codeidoscope.response.Response;
import com.github.codeidoscope.response.ResponseBuilder;
import com.github.codeidoscope.StatusCodes;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DirectoryHandler implements RouteHandler {
    private final HandlersHelper handlersHelper = new HandlersHelper();
    private String getDateTimeNow = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());

    @Override
    public Response respondToRequest(Request request) throws IOException {
        String filePath = handlersHelper.generateFilePath(request);

        String generatedPage = generateBodyFromDirectory(filePath);
        int bodyLength = generatedPage.length();
        InputStream generatedContent = new ByteArrayInputStream(generatedPage.getBytes());

        Body body = new Body(generatedContent);

        String contentType = "text/html";
        ResponseBuilder responseBuilder = new ResponseBuilder(StatusCodes.OK)
                .addHeader("Date", getDateTimeNow)
                .addHeader("Content-Type", contentType)
                .addHeader("Content-Length", String.valueOf(bodyLength));

        Header header = new Header(responseBuilder.setHeader());
        return new Response(header, body);
    }

    public String generateBodyFromDirectory(String path) throws IOException {
        StringBuilder htmlContent = new StringBuilder();
        File[] files = new File(path).listFiles();

        if (files != null) {
            for (File file : files) {
                String filePath;
                filePath = file.getCanonicalPath();
                filePath = removeBasePathFromPath(filePath);
                String link = createHtmlLink(filePath);
                addToHtmlContent(link, htmlContent);
            }
        }
        return addHtmlContentToBody(htmlContent);
    }

    public String removeBasePathFromPath(String path) throws IOException {
        Path absolutePath = Paths.get(path);
        Path relativePath = Paths.get(Configuration.getInstance().getContentRootPath()).relativize(absolutePath);
        return relativePath.toString();
    }

    public String createHtmlLink(String filePath) {
        return String.format("<li><a href=\"/%s\">%s</a></li>", filePath, filePath);
    }

    public void addToHtmlContent(String content, StringBuilder htmlContent) {
        htmlContent.append(content);
    }

    public String addHtmlContentToBody(StringBuilder htmlContent) {
        String htmlTemplatePath = "<!DOCTYPE html>\n<head>\n</head>\n<body>\n$body\n</body>\n</html>\n";
        return htmlTemplatePath.replace("$body", htmlContent);
    }
}
