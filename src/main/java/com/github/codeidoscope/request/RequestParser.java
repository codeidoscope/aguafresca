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

        if (firstLineOfRequest != null) {
            LinkedHashMap<String, String> headers = getHeaders(bufferedReader);
            String contentLengthValue = headers.get("Content-Length");
            String body = getBody(contentLengthValue, bufferedReader);

            Request request = createRequestFromLine(firstLineOfRequest);
            request.setHeaders(headers);
            request.setBody(body);

            return request;
        } else {
            return new Request();
        }
    }

    Request createRequestFromLine(String firstLineOfRequest) {
        String[] splitLine = firstLineOfRequest.split("\r\n")[0].split(" ");

        Request parsedRequest = new Request();
        parsedRequest.setMethod(splitLine[0]);
        parsedRequest.setPath(splitLine[1]);
        parsedRequest.setProtocol(splitLine[2]);

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

    private String getBody(String contentLengthValue, BufferedReader bufferedReader) throws IOException {
        if (contentLengthValue == null) {
            return null;
        } else {
            int contentLengthAsInt = Integer.parseInt(contentLengthValue);
            StringBuilder body = new StringBuilder();
            for (int i = 0; i < contentLengthAsInt; i++) {
                body.append((char) bufferedReader.read());
            }
            return body.toString().trim();
        }
    }
}