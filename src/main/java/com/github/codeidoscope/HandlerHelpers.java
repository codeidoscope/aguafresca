package com.github.codeidoscope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class HandlerHelpers {

    HandlerHelpers() {
    }

    String getContentType(String path) throws IOException {
        return Files.probeContentType(Paths.get(path));
    }

    Boolean shouldBeAttachment(String type, int length) {
        int TenMbInBytes = 10485760;
        return type.equals("application/pdf") && length > TenMbInBytes;
    }

    String generateFilePath(Request request) throws IOException {
        String contentRootPath = Configuration.getInstance().getContentRootPath();
        return contentRootPath + request.getPath();
    }
}