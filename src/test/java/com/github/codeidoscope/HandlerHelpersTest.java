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
    void contentIsRetrievedFromAGivenFilePath() throws IOException {
        HandlerHelpers handlerHelpers = new HandlerHelpers();

        String filePath = System.getProperty("user.dir") + "/testdirectory/testfile.txt";
        String expectedContentType = "text/plain";

        String generatedContentType = handlerHelpers.getContentType(filePath);

        assertEquals(expectedContentType, generatedContentType);
    }

    @Test
    void filePathIsGeneratedFromRequest() throws IOException {
        HandlerHelpers handlerHelpers = new HandlerHelpers();
        Request request = new Request();
        request.setProtocol("HTTP/1.1");
        request.setMethod("GET");
        request.setPath("/public");

        String expectedFilePath = Configuration.getInstance().getContentRootPath() + request.getPath();
        String generatedFilePath = handlerHelpers.generateFilePath(request);

        assertEquals(expectedFilePath, generatedFilePath);
    }

    @Test
    void shouldBeAttachmentReturnsTrueIfFileIsPdfLargerThanTenMb() {
        HandlerHelpers handlerHelpers = new HandlerHelpers();

        assertTrue(handlerHelpers.shouldBeAttachment(pdfFileType, lengthLargerThanTenMb));
    }

    @Test
    void shouldBeAttachmentReturnsFalseIfFileIsPdfSmallerThanTenMb() {
        HandlerHelpers handlerHelpers = new HandlerHelpers();

        assertFalse(handlerHelpers.shouldBeAttachment(pdfFileType, lengthSmallerThanTenMb));
    }

    @Test
    void shouldBeAttachmentReturnsFalseIfFileIsNotPdfLargerThanTenMb() {
        HandlerHelpers handlerHelpers = new HandlerHelpers();

        assertFalse(handlerHelpers.shouldBeAttachment(imageFileType, lengthLargerThanTenMb));
    }

    @Test
    void shouldBeAttachmentReturnsFalseIfFileIsNotPdfSmallerThanTenMb() {
        HandlerHelpers handlerHelpers = new HandlerHelpers();

        assertFalse(handlerHelpers.shouldBeAttachment(imageFileType, lengthSmallerThanTenMb));
    }
}