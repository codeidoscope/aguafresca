package com.github.codeidoscope;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HandlersHelperTest {
    private int lengthLargerThanTenMb = 20485760;
    private int lengthSmallerThanTenMb = 100;
    private String pdfFileType = "application/pdf";
    private String imageFileType = "image/png";

    @Test
    void contentTypeIsRetrievedFromAGivenFilePath() throws IOException {
        HandlersHelper handlersHelper = new HandlersHelper();
        String filePath = System.getProperty("user.dir") + "/testdirectory/testfile.txt";
        String expectedContentType = "text/plain";
        String contentType = handlersHelper.getContentType(filePath);

        assertEquals(expectedContentType, contentType);
    }

    @Test
    void filePathIsGeneratedFromRequest() throws IOException {
        HandlersHelper handlersHelper = new HandlersHelper();
        Request request = new Request();
        request.setMethod("GET");
        request.setPath("/public");
        request.setProtocol("HTTP/1.1");

        String expectedFilePath = Configuration.getInstance().getContentRootPath() + request.getPath();

        String filePath = handlersHelper.generateFilePath(request);

        assertEquals(expectedFilePath, filePath);
    }

    @Test
    void shouldBeAttachmentReturnsTrueIfFileIsPdfAndLargerThanTenMb() {
        HandlersHelper handlersHelper = new HandlersHelper();

        assertTrue(handlersHelper.shouldBeAttachment(pdfFileType, lengthLargerThanTenMb));
    }

    @Test
    void shouldBeAttachmentReturnsFalseIfFileIsPdfAndSmallerThanTenMb() {
        HandlersHelper handlersHelper = new HandlersHelper();

        assertFalse(handlersHelper.shouldBeAttachment(pdfFileType, lengthSmallerThanTenMb));
    }

    @Test
    void shouldBeAttachmentReturnsFalseIfFileIsNotPdfAndLargerThanTenMb() {
        HandlersHelper handlersHelper = new HandlersHelper();

        assertFalse(handlersHelper.shouldBeAttachment(imageFileType, lengthLargerThanTenMb));
    }

    @Test
    void shouldBeAttachmentReturnsFalseIfFileIsNotPdfAndSmallerThanTenMb() {
        HandlersHelper handlersHelper = new HandlersHelper();

        assertFalse(handlersHelper.shouldBeAttachment(imageFileType, lengthSmallerThanTenMb));
    }
}