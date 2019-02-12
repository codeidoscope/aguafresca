package com.github.codeidoscope;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class ServerRouterTest {

    @BeforeEach
    void setUp() throws IOException {
        Configuration.getInstance().setPortNumber(8080);
        Configuration.getInstance().setContentRootPath(System.getProperty("user.dir"));
    }

    @Test
    void returnsAHeaderAndTheContentOfATextFileAsTheBodyWhenAFileIsRequested() throws IOException {
        Request request = new Request();
        request.setMethod("GET");
        request.setPath("/testdirectory/testfile.txt");
        request.setProtocol("HTTP/1.1");

        ServerRouter serverRouter = new ServerRouter();
        Header headers = new Header("HTTP/1.1 200 OK\nDate: Fri, 11 Jan 2019 10:30:00 GMT\nContent-Type: text/plain\nContent-Length: 10\nAccept-Ranges: bytes");
        Response expectedResponse = new Response(headers, new Body("Test file."));

        assertFalse(serverRouter.route(request).getHeadersToString().isEmpty());
        assertEquals(expectedResponse.getBodyToString(), serverRouter.route(request).getBodyToString());
    }

    @Test
    void returnsAHeaderAndAnHtmlListingOfTheContentOfADirectoryAsTheBodyWhenADirectoryIsRequested() throws IOException {
        Request request = new Request();
        request.setMethod("GET");
        request.setPath("/testdirectory");
        request.setProtocol("HTTP/1.1");

        ServerRouter serverRouter = new ServerRouter();
        Header headers = new Header("HTTP/1.1 200 OK\nDate: Fri, 11 Jan 2019 10:30:00 GMT\nContent-Type: text/html\nContent-Length: 219\nAccept-Ranges: bytes");
        Response expectedResponse = new Response(headers, new Body("<!DOCTYPE html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "<li><a href=\"/testdirectory/othertestfile.txt\">testdirectory/othertestfile.txt</a></li>" +
                "<li><a href=\"/testdirectory/testfile.txt\">testdirectory/testfile.txt</a></li>\n" +
                "</body>\n" +
                "</html>\n"));

        assertFalse(serverRouter.route(request).getHeadersToString().isEmpty());
        assertEquals(expectedResponse.getBodyToString(), serverRouter.route(request).getBodyToString());
    }

    @Test
    void returnsAHeaderAndA404ErrorMessageAsTheBodyWhenTheRequestIsInvalid() throws IOException {
        Request request = new Request();
        request.setMethod("GET");
        request.setPath("/testdirectory/foo.txt");
        request.setProtocol("HTTP/1.1");

        ServerRouter serverRouter = new ServerRouter();
        Header headers = new Header("HTTP/1.1 404 Not Found\nDate: Fri, 11 Jan 2019 10:30:00 GMT\nContent-Type: text/plain\nContent-Length: 15\nAccept-Ranges: bytes");
        Response expectedResponse = new Response(headers, new Body("404 - NOT FOUND"));

        assertFalse(serverRouter.route(request).getHeadersToString().isEmpty());
        assertEquals(expectedResponse.getBodyToString(), serverRouter.route(request).getBodyToString());
    }

    @Test
    void returnsAHeaderAndAnHtmlFormAsTheBodyWhenAFormIsRequested() throws IOException {
        Request request = new Request();
        request.setMethod("GET");
        request.setPath("/form");
        request.setProtocol("HTTP/1.1");

        ServerRouter serverRouter = new ServerRouter();
        Header header = new Header("HTTP/1.1 200 OK\nDate: Fri, 11 Jan 2019 10:30:00 GMT\nContent-Type: text/html\nContent-Length: 845\nAccept-Ranges: bytes");
        String form = new FormHandler().generateHtmlForm();
        Response expectedResponse = new Response(header, new Body(form));

        assertFalse(serverRouter.route(request).getHeadersToString().isEmpty());
        assertEquals(expectedResponse.getBodyToString(), serverRouter.route(request).getBodyToString());
    }

    @Test
    void returnsAHeaderAndAnHtmlPageDisplayingRequestParametersWhenAFormDataIsRequested() throws IOException {
        Request request = new Request();
        request.setMethod("POST");
        request.setPath("/form_results");
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
        Body body = new Body("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "<p>Your name is: King Arthur</p><br>\n" +
                "<p>Your quest is to: Defeat the Rabbit of Caerbannog</p><br>\n" +
                "<p>Your favourite colour is: blue</p><br>\n" +
                "<p>According to you, the average air speed velocity of a laden swallow in mph is: 4mph</p><br>\n" +
                "</body>\n" +
                "</html>");

        ServerRouter serverRouter = new ServerRouter();
        Header header = new Header("HTTP/1.1 200 OK\nDate: Fri, 11 Jan 2019 10:30:00 GMT\nContent-Type: text/html\nContent-Length: 845\nAccept-Ranges: bytes");
        Response expectedResponse = new Response(header, body);

        assertFalse(serverRouter.route(request).getHeadersToString().isEmpty());
        assertEquals(expectedResponse.getBodyToString(), serverRouter.route(request).getBodyToString());
    }
}