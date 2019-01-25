package com.github.codeidoscope;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DirectoryHandlerTest {

    private DirectoryHandler directoryHandler = new DirectoryHandler();
    private String contentRootPath = Configuration.getInstance().getContentRootPath();

    DirectoryHandlerTest() throws IOException {
    }

    @BeforeEach
    void setUp() throws IOException {
        Configuration.getInstance().setPortNumber(8080);
        Configuration.getInstance().setContentRootPath(System.getProperty("user.dir"));
    }

    @Test
    void extractsTheBasePathFromAGivenPath() throws IOException {
        String filePath = contentRootPath + "/foo.txt";
        String expectedExtractedPath = "foo.txt";
        String extractedPath = directoryHandler.removeBasePathFromPath(filePath);

        assertEquals(expectedExtractedPath, extractedPath);
    }

    @Test
    void returnsAnHtmlLinkStringGivenAFilePath() throws IOException {
        String filePath = new File(contentRootPath + "/foo.txt").getAbsolutePath();
        String extractedPath = directoryHandler.removeBasePathFromPath(filePath);
        String testHtmlLink = String.format("<li><a href=\"/%s\">%s</a></li>", extractedPath, extractedPath);
        String htmlLink = directoryHandler.createHtmlLink(extractedPath);

        assertEquals(testHtmlLink, htmlLink);
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

        String testHtmlContent = "<li><a href=\"/foo.txt\">foo.txt</a></li>" +
                "<li><a href=\"/bar.txt\">bar.txt</a></li>" +
                "<li><a href=\"/baz.txt\">baz.txt</a></li>";

        assertEquals(testHtmlContent, htmlContent.toString());
    }

    @Test
    void returnsAnHtmlBodyString() {
        StringBuilder htmlContent = new StringBuilder();
        String htmlLinkFoo = directoryHandler.createHtmlLink("foo.txt");
        directoryHandler.addToHtmlContent(htmlLinkFoo, htmlContent);

        String testHtmlBody = "<!DOCTYPE html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "<li><a href=\"/foo.txt\">foo.txt</a></li>\n" +
                "</body>\n" +
                "</html>\n";
        String htmlBody = directoryHandler.addHtmlContentToBody(htmlContent);

        assertEquals(testHtmlBody, htmlBody);
    }

    @Test
    void generatesHtmlPageBasedOnContentOfDirectory() throws IOException {
        String directoryPath = Configuration.getInstance().getContentRootPath() + "/testdirectory";
        String expectedBody = "<!DOCTYPE html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "<li><a href=\"/testdirectory/othertestfile.txt\">testdirectory/othertestfile.txt</a></li><li><a href=\"/testdirectory/testfile.txt\">testdirectory/testfile.txt</a></li>\n" +
                "</body>\n" +
                "</html>\n";
        String body = directoryHandler.generateBodyFromDirectory(directoryPath);

        assertEquals(expectedBody, body);
    }

    @Test
    void returnsARequestContainingAGeneratedBody() throws IOException {
        DirectoryHandler directoryHandler = new DirectoryHandler();

        Request request = new Request();
        request.setMethod("GET");
        request.setProtocol("HTTP/1.1");
        request.setPath("/testdirectory");

        Body expectedBody = new Body("<!DOCTYPE html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "<li><a href=\"/testdirectory/othertestfile.txt\">testdirectory/othertestfile.txt</a></li><li><a href=\"/testdirectory/testfile.txt\">testdirectory/testfile.txt</a></li>\n" +
                "</body>\n" +
                "</html>\n");
        Response response = directoryHandler.respondToRequest(request);

        // Do I need to check the header as well?
        assertEquals(expectedBody.getBodyString(), response.getBodyToString());
    }
}