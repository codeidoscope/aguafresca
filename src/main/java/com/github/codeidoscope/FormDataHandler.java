package com.github.codeidoscope;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

public class FormDataHandler implements RouteHandler {
    private String getDateTimeNow = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());

    @Override
    public Response respondToRequest(Request request) {
        LinkedHashMap<String, String> requestBody = parseBody(request.getBody());

        String generatedPage = generateHtmlPageFromResults(requestBody);
        int bodyLength = generatedPage.length();
        InputStream generatedContent = new ByteArrayInputStream(generatedPage.getBytes());

        Body body = new Body(generatedContent);

        String contentType = "text/html";
        ResponseBuilder responseBuilder = new ResponseBuilder(StatusCodes.Status.OK)
                .addHeader("Date", getDateTimeNow)
                .addHeader("Content-Type", contentType)
                .addHeader("Content-Length", String.valueOf(bodyLength));

        Header header = new Header(responseBuilder.setHeader());
        return new Response(header, body, contentType);
    }

    String generateHtmlPageFromResults(LinkedHashMap<String, String> requestBody) {
        String name = requestBody.get("name");
        String quest = requestBody.get("quest");
        String colour = requestBody.get("colour");
        String speed = requestBody.get("speed");
        return String.format("<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<body>\n" +
                        "<p>Your name is: %s</p><br>\n" +
                        "<p>Your quest is to: %s</p><br>\n" +
                        "<p>Your favourite colour is: %s</p><br>\n" +
                        "<p>According to you, the average air speed velocity of a laden swallow in mph is: %smph</p><br>\n" +
                        "</body>\n" +
                        "</html>",
                name,
                quest,
                colour,
                speed);
    }

    LinkedHashMap<String, String> parseBody(String body) {
        LinkedHashMap<String, String> bodyFields = new LinkedHashMap<>();
        String[] elementsOfLine = body.split("&");

        for (String element : elementsOfLine) {
            String[] splitElement = element.split("=");
            String key = replacePlusSignWithSpace(splitElement[0]);
            String value = replacePlusSignWithSpace(splitElement[1]);
            bodyFields.put(key, value);
        }
        return bodyFields;
    }

    String replacePlusSignWithSpace(String bodyElement) {
        String newBodyElement;
        if (bodyElement.contains("+")) {
            newBodyElement = bodyElement.replace("+", " ");
        } else {
            return bodyElement;
        }
        return newBodyElement;
    }
}
