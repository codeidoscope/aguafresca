package com.github.codeidoscope.handlers;

import com.github.codeidoscope.Configuration;
import com.github.codeidoscope.request.Request;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HandlersHelper {
    private final int TEN_MEGABYTES_IN_BYTES = 10485760;

    public String getContentType(String path) throws IOException {
        return Files.probeContentType(Paths.get(path));
    }

    public Boolean shouldBeAttachment(String type, long length) {
        return type.equals("application/pdf") && length > TEN_MEGABYTES_IN_BYTES;
    }

    public String generateFilePath(Request request) throws IOException {
        String contentRootPath = Configuration.getInstance().getContentRootPath();
        return contentRootPath + request.getPath();
    }
}