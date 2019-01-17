package com.github.codeidoscope;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class DirectoryHandlerTest {

    private DirectoryHandler directoryHandler = new DirectoryHandler();

    @BeforeEach
    void setUp() {
        Configuration.getInstance().setPortNumber(8080);
        Configuration.getInstance().setDirectoryPath(System.getProperty("user.dir"));
        Configuration.getInstance().setSubPath("");
        DirectoryHandler.htmlContent = new StringBuilder();
    }

    @Test
    void extractsTheNameOfADirectoryFromPath() {
        Configuration.getInstance().setSubPath("/public");
        String testPath = Configuration.getInstance().getDirectoryPath() + Configuration.getInstance().getSubPath();
        String expectedExtractedPath = "/public";
        String extractedPath = directoryHandler.extractDirectoryPath(testPath);

        assertEquals(expectedExtractedPath, extractedPath);
    }

    @Test
    void extractsTheNameOfADirectoryFromPathWhenNoSubPathGiven() {
        String testPath = Configuration.getInstance().getDirectoryPath();
        String expectedExtractedPath = "/";
        String extractedPath = directoryHandler.extractDirectoryPath(testPath);

        assertEquals(expectedExtractedPath, extractedPath);
    }

    @Test
    void extractsTheNameOfAFileFromPath() {
        String testPath = Configuration.getInstance().getDirectoryPath() + Configuration.getInstance().getSubPath() + "/foo.txt";
        String expectedExtractedPath = "/foo.txt";
        String extractedPath = directoryHandler.extractPath(testPath);

        assertEquals(expectedExtractedPath, extractedPath);
    }

    @Test
    void extractsTheNameOfAFileFromPathWhenNoSubPathGiven() {
        String testPath = Configuration.getInstance().getDirectoryPath() + "/foo.txt";
        String expectedExtractedPath = "/foo.txt";
        String extractedPath = directoryHandler.extractPath(testPath);

        assertEquals(expectedExtractedPath, extractedPath);
    }

    @Test
    void returnsAnHtmlLinkStringGivenAFilePath() {
        String filePath = new File(Configuration.getInstance().getDirectoryPath() + "/foo.txt").getAbsolutePath();
        String testHtmlLink = String.format("<li><a href=\"%s\">%s</a></li>", filePath, directoryHandler.extractPath(filePath));
        String htmlLink = directoryHandler.createHtmlLink(filePath);

        assertEquals(testHtmlLink, htmlLink);
    }

    @Test
    void returnsAStringOfContentToBeAddedToHtmlBody() {
        String filePathFoo = new File(Configuration.getInstance().getDirectoryPath() + "/foo.txt").getAbsolutePath();
        String htmlLinkFoo = directoryHandler.createHtmlLink(filePathFoo);

        String filePathBar = new File(Configuration.getInstance().getDirectoryPath() + "/bar.txt").getAbsolutePath();
        String htmlLinkBar = directoryHandler.createHtmlLink(filePathBar);

        String filePathBaz = new File(Configuration.getInstance().getDirectoryPath() + "/baz.txt").getAbsolutePath();
        String htmlLinkBaz = directoryHandler.createHtmlLink(filePathBaz);

        String testHtmlContent = "<li><a href=\"/Users/marion/Desktop/Engineering/aguafresca/foo.txt\">/foo.txt</a></li>" +
                "<li><a href=\"/Users/marion/Desktop/Engineering/aguafresca/bar.txt\">/bar.txt</a></li>" +
                "<li><a href=\"/Users/marion/Desktop/Engineering/aguafresca/baz.txt\">/baz.txt</a></li>";

        directoryHandler.addToHtmlContent(htmlLinkFoo);
        directoryHandler.addToHtmlContent(htmlLinkBar);
        directoryHandler.addToHtmlContent(htmlLinkBaz);

        String htmlContent = DirectoryHandler.htmlContent.toString();
        assertEquals(testHtmlContent, htmlContent);
    }

    @Test
    void returnsAnHtmlBodyString() {
        String filePathFoo = new File(Configuration.getInstance().getDirectoryPath() + "/foo.txt").getAbsolutePath();
        String htmlLinkFoo = directoryHandler.createHtmlLink(filePathFoo);
        directoryHandler.addToHtmlContent(htmlLinkFoo);

        String htmlContent = DirectoryHandler.htmlContent.toString();
        String testHtmlBody = "<!DOCTYPE html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "<li><a href=\"/Users/marion/Desktop/Engineering/aguafresca/foo.txt\">/foo.txt</a></li>\n" +
                "</body>\n" +
                "</html>\n";
        String htmlBody = directoryHandler.addHtmlContentToBody(htmlContent);
        assertEquals(testHtmlBody, htmlBody);
    }

    @Test
    void generatesHtmlPageBasedOnContentOfDirectory() {
        String directoryPath = Configuration.getInstance().getDirectoryPath() + "/testdirectory";
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