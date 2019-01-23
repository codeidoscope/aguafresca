package com.github.codeidoscope;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryHandler implements RouteHandler {
    @Override
    public Response respondToRequest(Request request) throws IOException {
        HeaderGenerator headerGenerator = new HeaderGenerator();

        String contentRootPath = Configuration.getInstance().getContentRootPath();
        String filePath = contentRootPath + request.getPath();

        Body body = new Body(generateBodyFromDirectory(filePath).getBytes());
        Header header = headerGenerator.generate("200 OK", getMimeType(request), body.getLength());

        return new Response(header, body);
    }

    String removeBasePathFromPath(String path) throws IOException {
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

    String generateBodyFromDirectory(String path) throws IOException {
        StringBuilder htmlContent = new StringBuilder();
        File[] files = new File(path).listFiles();

        if (files != null) {
            for (File file : files) {
                String filePath = null;
                filePath = file.getCanonicalPath();
                filePath = removeBasePathFromPath(filePath);
                String link = createHtmlLink(filePath);
                addToHtmlContent(link, htmlContent);
            }
        }
        return addHtmlContentToBody(htmlContent);
    }

    private String getMimeType(Request request) {
        return javax.activation.MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(request.getPath());
    }
}
