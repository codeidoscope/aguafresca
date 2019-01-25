package com.github.codeidoscope;

class DefaultHandler implements RouteHandler {
    @Override
    public Response respondToRequest(Request request) {
        HeaderGenerator headerGenerator = new HeaderGenerator();

        Body body = new Body("404 - NOT FOUND");
        Header header = headerGenerator.generate("404 Not Found", getMimeType(request), body.getLength());

        return new Response(header, body);
    }

    private String getMimeType(Request request) {
        return javax.activation.MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(request.getPath());
    }
}
