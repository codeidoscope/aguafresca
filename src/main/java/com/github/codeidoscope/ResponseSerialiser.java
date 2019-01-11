package com.github.codeidoscope;

public class ResponseSerialiser {

    String serialise(Response response) {
        return response.getHeaders() + "\n\r\n" + response.getBody();
    }
}
