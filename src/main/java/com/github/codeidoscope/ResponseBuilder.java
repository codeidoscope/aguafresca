package com.github.codeidoscope;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseBuilder {
    private StatusCodes.Status statusCode;
    private final String PROTOCOL = "HTTP/1.1";
    private Map<String, String> header = new LinkedHashMap<>();
    private byte[] body;
    private String CRLF = "\r\n";

    public ResponseBuilder(StatusCodes.Status statusCode) {
        this.statusCode = statusCode;
    }

    public ResponseBuilder addHeader(String key, String value) {
        header.put(key, value);
        return this;
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public String setHeader() {
        StringBuilder responseString = new StringBuilder();

        responseString.append(String.format("%s %s%s", PROTOCOL, statusCode.message, CRLF));

        for (Map.Entry<String, String> field : header.entrySet()) {
            responseString.append(String.format("%s: %s%s", field.getKey(), field.getValue(), CRLF));
        }

        return responseString.toString().trim();
    }

    public ResponseBuilder setBody(byte[] body) {
        this.body = body;
        return this;
    }

    public byte[] getBody() {
        return body;
    }
}
