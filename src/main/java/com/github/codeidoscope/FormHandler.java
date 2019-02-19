package com.github.codeidoscope;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class FormHandler implements RouteHandler {
    private String getDateTimeNow = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now());

    @Override
    public Response respondToRequest(Request request) {
        Body body = new Body(generateHtmlForm());

        String contentType = "text/html";
        int bodyLength = body.getLength();

        ResponseBuilder responseBuilder = new ResponseBuilder(StatusCodes.Status.OK)
                .addHeader("Date", getDateTimeNow)
                .addHeader("Content-Type", contentType)
                .addHeader("Content-Length", String.valueOf(bodyLength))
                .addHeader("Accept-Ranges", "bytes");

        Header header = new Header(responseBuilder.setHeader());
        return new Response(header, body);
    }

    String generateHtmlForm() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "<form method=\"POST\" action=\"/form_results\">\n" +
                "<p>What is your name?</p>\n" +
                "<input type=\"text\" name=\"name\"><br>\n" +
                "<p>What is your quest?</p>\n" +
                "<select name=\"quest\">\n" +
                "  <option value=\"Seek the Holy Grail\">Seek the Holy Grail</option>\n" +
                "  <option value=\"Find a shruberry for the Knights Who Say Ni\">Find a shruberry for the Knights Who Say Ni</option>\n" +
                "  <option value=\"Defeat the Rabbit of Caerbannog\">Defeat the Rabbit of Caerbannog</option>\n" +
                "  <option value=\"Have fun at Castle Anthrax\">Have fun at Castle Anthrax</option>\n" +
                "  <option value=\"Inherit curtains and sing all day long\">Inherit curtains and sing all day long</option>\n" +
                "</select><br>\n" +
                "<p>What is your favourite colour?</p>\n" +
                "  <input type=\"radio\" name=\"colour\" value=\"Blue\"> Blue<br>\n" +
                "  <input type=\"radio\" name=\"colour\" value=\"Yellow\"> Yellow<br><br>\n" +
                "<p>What is the average air speed velocity of a laden swallow in mph? (African or European swallow)</p>\n" +
                "<input type=\"number\" name=\"speed\" min=\"1\" max=\"25\"><br><br>\n" +
                "<input type=\"submit\" value=\"Submit\">\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>";
    }
}
