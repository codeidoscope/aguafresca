package com.github.codeidoscope;

import java.util.LinkedHashMap;

public class FormDataHandler implements RouteHandler {
    private final HandlersHelper handlersHelper = new HandlersHelper();

    @Override
    public Response respondToRequest(Request request) {
        HeaderGenerator headerGenerator = new HeaderGenerator();

        LinkedHashMap<String, String> requestBody = parseBody(request.getBody());

        Body body = new Body(generateHtmlPageFromResults(requestBody));

        String contentType = "text/html";
        int bodyLength = body.getLength();
        Boolean shouldBeAttachment = handlersHelper.shouldBeAttachment(contentType, bodyLength);

        Header header = headerGenerator.generate(StatusCodes.Status.OK.message, contentType, bodyLength, shouldBeAttachment);

        return new Response(header, body);
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