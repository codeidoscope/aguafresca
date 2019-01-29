package com.github.codeidoscope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class DefaultHandler implements RouteHandler {
    @Override
    public Response respondToRequest(Request request) throws IOException {
        HeaderGenerator headerGenerator = new HeaderGenerator();

        Body body = new Body("404 - NOT FOUND");
        Header header = headerGenerator.generate("404 Not Found", getContentType(request.getPath()), body.getLength());

        return new Response(header, body);
    }

    private String getContentType(String path) throws IOException {
        return Files.probeContentType(Paths.get(path));
    }
}
