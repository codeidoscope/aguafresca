package com.github.codeidoscope;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class FormDataHandlerTest {

    private FormDataHandler formDataHandler;

    @BeforeEach
    void setUp() {
        formDataHandler = new FormDataHandler();
    }

    @Test
    void returnsAResponseWithTheCorrectBodyForAForm() {
        Request request = new Request();
        request.setMethod("POST");
        request.setPath("form_results");
        request.setProtocol("HTTP/1.1");
        LinkedHashMap<String, String> expectedHeaders = new LinkedHashMap<>() {{
            put("Host", "localhost:8080");
            put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            put("Accept-Encoding", "gzip, deflate, br");
            put("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8,fr;q=0.7");
            put("Content-Length", "25");
        }};
        request.setHeaders(expectedHeaders);
        request.setBody("name=King+Arthur&quest=Defeat+the+Rabbit+of+Caerbannog&colour=blue&speed=4");
        Body expectedBody = new Body("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "<p>Your name is: King Arthur</p><br>\n" +
                "<p>Your quest is to: Defeat the Rabbit of Caerbannog</p><br>\n" +
                "<p>Your favourite colour is: blue</p><br>\n" +
                "<p>According to you, the average air speed velocity of a laden swallow in mph is: 4mph</p><br>\n" +
                "</body>\n" +
                "</html>");
        Response response = formDataHandler.respondToRequest(request);

        assertFalse(response.getHeadersToString().isEmpty());
        assertEquals(expectedBody.getBodyString(), response.getBodyToString());


    }

    @Test
    void generatesAnHtmlPageDisplayingDataFromTheRequestBody() {
        LinkedHashMap<String, String> bodyFields = new LinkedHashMap<>() {{
            put("name", "King Arthur");
            put("quest", "Seek the Holy Grail");
            put("colour", "Blue");
            put("speed", "24");
        }};

        String expectedHtmlString = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "<p>Your name is: King Arthur</p><br>\n" +
                "<p>Your quest is to: Seek the Holy Grail</p><br>\n" +
                "<p>Your favourite colour is: Blue</p><br>\n" +
                "<p>According to you, the average air speed velocity of a laden swallow in mph is: 24mph</p><br>\n" +
                "</body>\n" +
                "</html>";

        assertEquals(expectedHtmlString, formDataHandler.generateHtmlPageFromResults(bodyFields));
    }

    @Test
    void parsesBodyIntoSeparateArguments() {
        String body = "name=King+Arthur&colour=blue&speed=3";

        LinkedHashMap<String, String> expectedBodyFields = new LinkedHashMap<>() {{
            put("name", "King Arthur");
            put("colour", "blue");
            put("speed", "3");
        }};

        assertEquals(expectedBodyFields, formDataHandler.parseBody(body));
    }

    @Test
    void replacesPlusSignWithSpaceInBodyElementWhenPlusSignPresentInString() {
        String bodyElement = "Arthur+King+Of+The+Britons";

        assertEquals("Arthur King Of The Britons", formDataHandler.replacePlusSignWithSpace(bodyElement));
    }

    @Test
    void returnsBodyElementWhenPlusSignNotPresentInString() {
        String bodyElement = "blue";

        assertEquals("blue", formDataHandler.replacePlusSignWithSpace(bodyElement));
    }

}