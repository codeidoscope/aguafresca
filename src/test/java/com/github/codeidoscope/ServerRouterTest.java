package com.github.codeidoscope;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
}