package com.github.codeidoscope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class FileHandler implements RouteHandler {
    private final HandlersHelper handlersHelper = new HandlersHelper();
    private String getDateTimeNow = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());

    @Override
    public Response respondToRequest(Request request) throws IOException {
        String contentRootPath = Configuration.getInstance().getContentRootPath();
        String filePath = contentRootPath + request.getPath();

        Body body = new Body(Files.readAllBytes(Paths.get(filePath)));

        String contentType = handlersHelper.getContentType(request.getPath());
        int bodyLength = body.getLength();
        Boolean shouldBeAttachment = handlersHelper.shouldBeAttachment(contentType, bodyLength);

        ResponseBuilder responseBuilder = new ResponseBuilder(StatusCodes.Status.OK)
                .addHeader("Date", getDateTimeNow)
                .addHeader("Content-Type", contentType)
                .addHeader("Content-Length", String.valueOf(bodyLength))
                .addHeader("Accept-Ranges", "bytes");

        if (shouldBeAttachment) {
            responseBuilder.addHeader("Content-Disposition", "attachment");
        }

        Header header = new Header(responseBuilder.setHeader());
        return new Response(header, body);
    }
}
