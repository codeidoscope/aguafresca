package com.github.codeidoscope.handlers;

import com.github.codeidoscope.response.Body;
import com.github.codeidoscope.Configuration;
import com.github.codeidoscope.response.Header;
import com.github.codeidoscope.request.Request;
import com.github.codeidoscope.response.Response;
import com.github.codeidoscope.response.ResponseBuilder;
import com.github.codeidoscope.StatusCodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class FileHandler implements RouteHandler {
    private final HandlersHelper handlersHelper = new HandlersHelper();
    private String getDateTimeNow = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());

    @Override
    public Response respondToRequest(Request request) throws IOException {
        String contentRootPath = Configuration.getInstance().getContentRootPath();
        String filePath = contentRootPath + request.getPath();
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);

        Body body = new Body(fileInputStream);
        long bodyLength = file.length();

        String contentType = handlersHelper.getContentType(request.getPath());
        Boolean shouldBeAttachment = handlersHelper.shouldBeAttachment(contentType, bodyLength);

        ResponseBuilder responseBuilder = new ResponseBuilder(StatusCodes.OK)
                .addHeader("Date", getDateTimeNow)
                .addHeader("Content-Type", contentType)
                .addHeader("Content-Length", String.valueOf(bodyLength));

        if (shouldBeAttachment) {
            responseBuilder.addHeader("Content-Disposition", "attachment");
        }

        Header header = new Header(responseBuilder.setHeader());
        return new Response(header, body);
    }
}
