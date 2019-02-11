package com.github.codeidoscope;

import java.io.IOException;

public class FormHandler implements RouteHandler {
    private final HandlersHelper handlersHelper = new HandlersHelper();

    @Override
    public Response respondToRequest(Request request) throws IOException {
        HeaderGenerator headerGenerator = new HeaderGenerator();

        Body body = new Body(generateHtmlForm());

        String contentType = "text/html";
        int bodyLength = body.getLength();
        Boolean shouldBeAttachment = handlersHelper.shouldBeAttachment(contentType, bodyLength);

        Header header = headerGenerator.generate(StatusCodes.Status.OK.message, contentType, bodyLength, shouldBeAttachment);

        return new Response(header, body);
    }

    String generateHtmlForm() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<p>What is your name?</p>\n" +
                "<input type=\"text\"><br>\n" +
                "<p>What is your quest?</p>\n" +
                "<select>\n" +
                "  <option value=\"holygrail\">To seek the Holy grail</option>\n" +
                "  <option value=\"shrubbery\">Find a shruberry for the Knights Who Say Ni</option>\n" +
                "  <option value=\"rabbit\">Defeat the Rabbit of Caerbannog</option>\n" +
                "  <option value=\"castle\">Have fun at Castle Anthrax</option>\n" +
                "  <option value=\"curtains\">Inherit curtains and sing all day long</option>\n" +
                "</select><br>\n" +
                "<p>What is your favourite colour?</p>\n" +
                "  <input type=\"radio\" name=\"colour\" value=\"blue\"> Blue<br>\n" +
                "  <input type=\"radio\" name=\"colour\" value=\"yellow\"> Yellow<br><br>\n" +
                "<p>What is the average air speed velocity of a laden swallow in mph? (African or European swallow)</p>\n" +
                "<input type=\"number\" name=\"quantity\" min=\"1\" max=\"25\"><br><br>\n" +
                "\n" +
                "<input type=\"submit\">\n" +
                "  \n" +
                "</body>\n" +
                "</html>";
    }
}
