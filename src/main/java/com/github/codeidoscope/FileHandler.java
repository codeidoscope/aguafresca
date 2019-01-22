package com.github.codeidoscope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class FileHandler implements RouteHandler {
    @Override
    public Response respondToRequest(Request request) throws IOException {
        RequestHeaderGenerator requestHeaderGenerator = new RequestHeaderGenerator();
        String contentRootPath = Configuration.getInstance().getContentRootPath();
        String filePath = contentRootPath + request.getPath();

        Body body = new Body(Files.readAllBytes(Paths.get(filePath)));
        Header header = requestHeaderGenerator.generate("200 OK", getMimeType(request), body.getLength());

        return new Response(header, body);
    }

    private String getMimeType(Request request) {
        return javax.activation.MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(request.getPath());
    }
}
