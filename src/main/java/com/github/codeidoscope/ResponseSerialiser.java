package com.github.codeidoscope;

class ResponseSerialiser {

    String serialise(Response response) {
        return response.getHeaders() + "\n\r\n" + response.getBody();
    }
}
