package com.github.codeidoscope;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class DirectoryHandlerTest {

    private DirectoryHandler directoryHandler = new DirectoryHandler();
    private String contentRootPath = Configuration.getInstance().getContentRootPath();

    @BeforeEach
    void setUp() {
        Configuration.getInstance().setPortNumber(8080);
        Configuration.getInstance().setContentRootPath(System.getProperty("user.dir"));
        DirectoryHandler.htmlContent = new StringBuilder();
    }

    @Test
    void extractsTheBasePathFromAGivenPath() {
        String filePath = contentRootPath + "/foo.txt";
        String expectedExtractedPath = "/foo.txt";
        String extractedPath = directoryHandler.removeBasePathFromPath(filePath);

        assertEquals(expectedExtractedPath, extractedPath);
    }

    @Test
    void returnsAnHtmlLinkStringGivenAFilePath() {
        String filePath = new File(contentRootPath + "/foo.txt").getAbsolutePath();
        String extractedPath = directoryHandler.removeBasePathFromPath(filePath);
        String testHtmlLink = String.format("<li><a href=\"%s\">%s</a></li>", filePath, extractedPath);
        String htmlLink = directoryHandler.createHtmlLink(filePath);

        assertEquals(testHtmlLink, htmlLink);
    }

    @Test
    void returnsAStringOfContentToBeAddedToHtmlBody() {
        String filePathFoo = new File(contentRootPath + "/foo.txt").getAbsolutePath();
        String htmlLinkFoo = directoryHandler.createHtmlLink(filePathFoo);

        String filePathBar = new File(contentRootPath + "/bar.txt").getAbsolutePath();
        String htmlLinkBar = directoryHandler.createHtmlLink(filePathBar);

        String filePathBaz = new File(contentRootPath + "/baz.txt").getAbsolutePath();
        String htmlLinkBaz = directoryHandler.createHtmlLink(filePathBaz);

        String testHtmlContent = String.format("<li><a href=\"%s/foo.txt\">/foo.txt</a></li>" +
                "<li><a href=\"%s/bar.txt\">/bar.txt</a></li>" +
                "<li><a href=\"%s/baz.txt\">/baz.txt</a></li>", contentRootPath, contentRootPath, contentRootPath);

        directoryHandler.addToHtmlContent(htmlLinkFoo);
        directoryHandler.addToHtmlContent(htmlLinkBar);
        directoryHandler.addToHtmlContent(htmlLinkBaz);

        String htmlContent = DirectoryHandler.htmlContent.toString();
        assertEquals(testHtmlContent, htmlContent);
    }

    @Test
    void returnsAnHtmlBodyString() {
        String filePathFoo = new File(contentRootPath + "/foo.txt").getAbsolutePath();
        String htmlLinkFoo = directoryHandler.createHtmlLink(filePathFoo);
        directoryHandler.addToHtmlContent(htmlLinkFoo);

        String htmlContent = DirectoryHandler.htmlContent.toString();
        String testHtmlBody = String.format("<!DOCTYPE html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "<li><a href=\"%s/foo.txt\">/foo.txt</a></li>\n" +
                "</body>\n" +
                "</html>\n", contentRootPath);
        String htmlBody = directoryHandler.addHtmlContentToBody(htmlContent);
        assertEquals(testHtmlBody, htmlBody);
    }

    @Test
    void generatesHtmlPageBasedOnContentOfDirectory() {
        String directoryPath = Configuration.getInstance().getContentRootPath() + "/testdirectory";
        String expectedBody = "<!DOCTYPE html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "<li><a href=\"/testdirectory/othertestfile.txt\">/testdirectory/othertestfile.txt</a></li><li><a href=\"/testdirectory/testfile.txt\">/testdirectory/testfile.txt</a></li>\n" +
                "</body>\n" +
                "</html>\n";
        String body = directoryHandler.generateBodyFromDirectory(directoryPath);

        assertEquals(expectedBody, body);
    }
}