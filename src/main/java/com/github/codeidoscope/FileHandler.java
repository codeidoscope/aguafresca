package com.github.codeidoscope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class FileHandler implements RouteHandler {
    private final HandlersHelper handlersHelper = new HandlersHelper();

    @Override
    public Response respondToRequest(Request request) throws IOException {
        HeaderGenerator headerGenerator = new HeaderGenerator();

        String contentRootPath = Configuration.getInstance().getContentRootPath();
        String filePath = contentRootPath + request.getPath();

        Body body = new Body(Files.readAllBytes(Paths.get(filePath)));

        String contentType = handlersHelper.getContentType(request.getPath());
        int bodyLength = body.getLength();
        Boolean shouldBeAttachment = handlersHelper.shouldBeAttachment(contentType, bodyLength);

        Header header = headerGenerator.generate(StatusCodes.Status.OK.message, contentType, bodyLength, shouldBeAttachment);

        return new Response(header, body);
    }
}
