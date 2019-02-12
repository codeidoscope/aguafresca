package com.github.codeidoscope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.logging.Level;

class RequestParser {

    Request parse(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String firstLineOfRequest = bufferedReader.readLine();

        LinkedHashMap<String, String> headers = getHeaders(bufferedReader);
        String contentLengthKey = headers.get("Content-Length");
        String body = getBody(contentLengthKey, bufferedReader);

        Request request = parseMethodPathProtocol(firstLineOfRequest);
        request.setHeaders(headers);
        request.setBody(body);

        return request;
    }

    Request parseMethodPathProtocol(String firstLineOfRequest) {
        String method = firstLineOfRequest.split("\n\r\n")[0].split(" ")[0];
        String path = firstLineOfRequest.split("\n\r\n")[0].split(" ")[1];
        String protocol = firstLineOfRequest.split("\n\r\n")[0].split(" ")[2];

        Request parsedRequest = new Request();
        parsedRequest.setMethod(method);
        parsedRequest.setPath(path);
        parsedRequest.setProtocol(protocol);

        return parsedRequest;
    }


    private LinkedHashMap<String, String> getHeaders(BufferedReader bufferedReader) throws IOException {
        LinkedHashMap<String, String> headers = new LinkedHashMap<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.equals("")) {
                break;
            } else {
                try {
                    String[] elementsOfLine = line.split(" ", 2);
                    String key = elementsOfLine[0].substring(0, elementsOfLine[0].length() - 1);
                    String value = elementsOfLine[1].trim();
                    headers.put(key, value);
                } catch (ArrayIndexOutOfBoundsException e) {
                    ServerLogger.serverLogger.log(Level.WARNING, "Error: " + e);
                }
            }
        }
        return headers;
    }

    private String getBody(String contentLengthKey, BufferedReader bufferedReader) throws IOException {
        if (contentLengthKey == null) {
            return null;
        } else {
            int contentLengthAsInt = Integer.parseInt(contentLengthKey);
            StringBuilder body = new StringBuilder();
            for (int i = 0; i < contentLengthAsInt; i++) {
                body.append((char) bufferedReader.read());
            }
            return body.toString().trim();
        }
    }
}