package com.github.codeidoscope;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HandlerHelpersTest {
    private int lengthLargerThanTenMb = 20485760;
    private int lengthSmallerThanTenMb = 100;
    private String pdfFileType = "application/pdf";
    private String imageFileType = "image/png";

    @Test
    void contentTypeIsRetrievedFromAGivenFilePath() throws IOException {
        HandlerHelpers handlerHelpers = new HandlerHelpers();
        String filePath = System.getProperty("user.dir") + "/testdirectory/testfile.txt";
        String expectedContentType = "text/plain";
        String contentType = handlerHelpers.getContentType(filePath);

        assertEquals(expectedContentType, contentType);
    }

    @Test
    void filePathIsGeneratedFromRequest() throws IOException {
        HandlerHelpers handlerHelpers = new HandlerHelpers();
        Request request = new Request();
        request.setMethod("GET");
        request.setPath("/public");
        request.setProtocol("HTTP/1.1");
        String expectedFilePath = Configuration.getInstance().getContentRootPath() + request.getPath();
        String filePath = handlerHelpers.generateFilePath(request);

        assertEquals(expectedFilePath, filePath);
    }

    @Test
    void shouldBeAttachmentReturnsTrueIfFileIsPdfAndLargerThanTenMb() {
        HandlerHelpers handlerHelpers = new HandlerHelpers();

        assertTrue(handlerHelpers.shouldBeAttachment(pdfFileType, lengthLargerThanTenMb));
    }

    @Test
    void shouldBeAttachmentReturnsFalseIfFileIsPdfAndSmallerThanTenMb() {
        HandlerHelpers handlerHelpers = new HandlerHelpers();

        assertFalse(handlerHelpers.shouldBeAttachment(pdfFileType, lengthSmallerThanTenMb));
    }

    @Test
    void shouldBeAttachmentReturnsFalseIfFileIsNotPdfAndLargerThanTenMb() {
        HandlerHelpers handlerHelpers = new HandlerHelpers();

        assertFalse(handlerHelpers.shouldBeAttachment(imageFileType, lengthLargerThanTenMb));
    }

    @Test
    void shouldBeAttachmentReturnsFalseIfFileIsNotPdfAndSmallerThanTenMb() {
        HandlerHelpers handlerHelpers = new HandlerHelpers();

        assertFalse(handlerHelpers.shouldBeAttachment(imageFileType, lengthSmallerThanTenMb));
    }
}