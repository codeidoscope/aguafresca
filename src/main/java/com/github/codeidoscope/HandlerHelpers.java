package com.github.codeidoscope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class HandlerHelpers {
    private final int TENMEGABYTESINBYTES = 10485760;

    HandlerHelpers() {
    }

    String getContentType(String path) throws IOException {
        return Files.probeContentType(Paths.get(path));
    }

    Boolean shouldBeAttachment(String type, int length) {
        return type.equals("application/pdf") && length > TENMEGABYTESINBYTES;
    }

    String generateFilePath(Request request) throws IOException {
        String contentRootPath = Configuration.getInstance().getContentRootPath();
        return contentRootPath + request.getPath();
    }
}