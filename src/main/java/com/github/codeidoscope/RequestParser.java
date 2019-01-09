package com.github.codeidoscope;

class RequestParser {

    Request parse(String request) {
        String method = request.split("\n\r\n")[0].split(" ")[0];
        String path = request.split("\n\r\n")[0].split(" ")[1];
        String protocol = request.split("\n\r\n")[0].split(" ")[2];

        Request parsedRequest = new Request();
        parsedRequest.setMethod(method);
        parsedRequest.setPath(path);
        parsedRequest.setProtocol(protocol);

        return parsedRequest;
    }

    String constructFilePath(String filePath) {
        return Configuration.getInstance().getDirectoryPath() + filePath;
    }
}