package com.github.codeidoscope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class FileHandler implements RouteHandler {
    private final HandlerHelpers handlerHelpers = new HandlerHelpers();

    @Override
    public Response respondToRequest(Request request) throws IOException {
        HeaderGenerator headerGenerator = new HeaderGenerator();

        String contentRootPath = Configuration.getInstance().getContentRootPath();
        String filePath = contentRootPath + request.getPath();

        Body body = new Body(Files.readAllBytes(Paths.get(filePath)));

        String contentType = handlerHelpers.getContentType(request.getPath());
        int bodyLength = body.getLength();
        Boolean shouldBeAttachment = handlerHelpers.shouldBeAttachment(contentType, bodyLength);

        Header header = headerGenerator.generate("200 OK", contentType, bodyLength, shouldBeAttachment);

        return new Response(header, body);
    }
}
