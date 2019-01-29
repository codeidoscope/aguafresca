package com.github.codeidoscope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class FileHandler implements RouteHandler {
    @Override
    public Response respondToRequest(Request request) throws IOException {
        HeaderGenerator headerGenerator = new HeaderGenerator();
        String contentRootPath = Configuration.getInstance().getContentRootPath();
        String filePath = contentRootPath + request.getPath();

        Body body = new Body(Files.readAllBytes(Paths.get(filePath)));
        Header header = headerGenerator.generate("200 OK", getContentType(filePath), body.getLength());

        return new Response(header, body);
    }

    private String getContentType(String path) throws IOException {
        return Files.probeContentType(Paths.get(path));
    }
}
