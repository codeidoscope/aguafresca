package com.github.codeidoscope;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

class ResponseSerialiser {

    byte[] serialise(Response response) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(response.getHeadersToBytes());
        outputStream.write("\n\r\n".getBytes());
        outputStream.write(response.getBodyToBytes());
        return outputStream.toByteArray();
    }
}
