package com.github.codeidoscope;

import java.io.IOException;

class DefaultHandler implements RouteHandler {
    private final HandlerHelpers handlerHelpers = new HandlerHelpers();

    @Override
    public Response respondToRequest(Request request) throws IOException {
        HeaderGenerator headerGenerator = new HeaderGenerator();

        Body body = new Body("404 - NOT FOUND");

        String contentType = handlerHelpers.getContentType(request.getPath());
        int bodyLength = body.getLength();
        Boolean shouldBeAttachment = handlerHelpers.shouldBeAttachment(contentType, bodyLength);

        Header header = headerGenerator.generate("404 Not Found", contentType, bodyLength, shouldBeAttachment);

        return new Response(header, body);
    }
}
