package com.github.codeidoscope.handlersTests;

import com.github.codeidoscope.Configuration;
import com.github.codeidoscope.handlers.DirectoryHandler;
import com.github.codeidoscope.request.Request;
import com.github.codeidoscope.response.Body;
import com.github.codeidoscope.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DirectoryHandlerTest {

    private DirectoryHandler directoryHandler = new DirectoryHandler();
    private String contentRootPath;

    DirectoryHandlerTest() {
    }

    @BeforeEach
    void setUp() throws IOException {
        Configuration.getInstance().setPortNumber(8080);
        contentRootPath = System.getProperty("user.dir");
        Configuration.getInstance().setContentRootPath(contentRootPath);
    }

    @Test
    void extractsTheBasePathFromAGivenPath() throws IOException {
        String filePath = new File(contentRootPath + "/foo.txt").getAbsolutePath();
        String extractedPath = directoryHandler.removeBasePathFromPath(filePath);

        assertEquals("foo.txt", extractedPath);
    }

    @Test
    void returnsAnHtmlLinkStringGivenAFilePath() throws IOException {
        String filePath = new File(contentRootPath + "/foo.txt").getAbsolutePath();
        String extractedPath = directoryHandler.removeBasePathFromPath(filePath);
        String expectedHtmlLink = String.format("<li><a href=\"/%s\">%s</a></li>", extractedPath, extractedPath);
        String htmlLink = directoryHandler.createHtmlLink(extractedPath);

        assertEquals(expectedHtmlLink, htmlLink);
    }

    @Test
    void returnsAStringOfContentToBeAddedToHtmlBody() {
        String htmlLinkFoo = directoryHandler.createHtmlLink("foo.txt");
        String htmlLinkBar = directoryHandler.createHtmlLink("bar.txt");
        String htmlLinkBaz = directoryHandler.createHtmlLink("baz.txt");

        StringBuilder htmlContent = new StringBuilder();
        directoryHandler.addToHtmlContent(htmlLinkFoo, htmlContent);
        directoryHandler.addToHtmlContent(htmlLinkBar, htmlContent);
        directoryHandler.addToHtmlContent(htmlLinkBaz, htmlContent);

        String expectedHtmlContent = "<li><a href=\"/foo.txt\">foo.txt</a></li>" +
                "<li><a href=\"/bar.txt\">bar.txt</a></li>" +
                "<li><a href=\"/baz.txt\">baz.txt</a></li>";

        assertEquals(expectedHtmlContent, htmlContent.toString());
    }

    @Test
    void returnsAnHtmlBodyString() {
        StringBuilder htmlContent = new StringBuilder();
        String htmlLinkFoo = directoryHandler.createHtmlLink("foo.txt");
        directoryHandler.addToHtmlContent(htmlLinkFoo, htmlContent);

        String expectedHtmlBody = "<!DOCTYPE html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "<li><a href=\"/foo.txt\">foo.txt</a></li>\n" +
                "</body>\n" +
                "</html>\n";

        String htmlBody = directoryHandler.addHtmlContentToBody(htmlContent);

        assertEquals(expectedHtmlBody, htmlBody);
    }

    @Test
    void generatesHtmlPageBasedOnContentOfDirectory() throws IOException {
        String directoryPath = Configuration.getInstance().getContentRootPath() + "/testdirectory";

        String expectedBody = "<!DOCTYPE html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "<li><a href=\"/testdirectory/othertestfile.txt\">testdirectory/othertestfile.txt</a></li>" +
                "<li><a href=\"/testdirectory/testfile.txt\">testdirectory/testfile.txt</a></li>\n" +
                "</body>\n" +
                "</html>\n";

        String htmlBody = directoryHandler.generateBodyFromDirectory(directoryPath);

        assertEquals(expectedBody, htmlBody);
    }

    @Test
    void returnsARequestContainingAGeneratedBody() throws IOException {
        DirectoryHandler directoryHandler = new DirectoryHandler();
        Request request = new Request();
        request.setMethod("GET");
        request.setProtocol("HTTP/1.1");
        request.setPath("/testdirectory");

        Body expectedBody = new Body(new ByteArrayInputStream("<!DOCTYPE html>\n<head>\n</head>\n<body>\n<li><a href=\"/testdirectory/othertestfile.txt\">testdirectory/othertestfile.txt</a></li><li><a href=\"/testdirectory/testfile.txt\">testdirectory/testfile.txt</a></li>\n</body>\n</html>\n".getBytes()));

        Response response = directoryHandler.respondToRequest(request);

        assertFalse(response.getHeadersToString().isEmpty());
        assertEquals(expectedBody.getBodyString(), response.getBodyToString());
    }
}