package com.github.codeidoscope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class HandlersHelper {
    private final int TEN_MEGABYTES_IN_BYTES = 10485760;

    String getContentType(String path) throws IOException {
        return Files.probeContentType(Paths.get(path));
    }

    Boolean shouldBeAttachment(String type, long length) {
        return type.equals("application/pdf") && length > TEN_MEGABYTES_IN_BYTES;
    }

    String generateFilePath(Request request) throws IOException {
        String contentRootPath = Configuration.getInstance().getContentRootPath();
        return contentRootPath + request.getPath();
    }
}