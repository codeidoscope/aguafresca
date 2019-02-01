package com.github.codeidoscope;

import java.io.IOException;

class DefaultHandler implements RouteHandler {
    private final HandlersHelper handlersHelper = new HandlersHelper();

    @Override
    public Response respondToRequest(Request request) throws IOException {
        HeaderGenerator headerGenerator = new HeaderGenerator();

        Body body = new Body("404 - NOT FOUND");

        String contentType = handlersHelper.getContentType(request.getPath());
        int bodyLength = body.getLength();
        Boolean shouldBeAttachment = handlersHelper.shouldBeAttachment(contentType, bodyLength);

        Header header = headerGenerator.generate(StatusCodes.Status.NOT_FOUND.message, contentType, bodyLength, shouldBeAttachment);

        return new Response(header, body);
    }
}
