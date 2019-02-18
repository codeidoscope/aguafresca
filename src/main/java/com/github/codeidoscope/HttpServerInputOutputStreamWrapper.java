package com.github.codeidoscope;

import java.io.IOException;
import java.io.OutputStream;

class HttpServerInputOutputStreamWrapper {

    void sendOutput(OutputStream outputStream, byte[] message) throws IOException {
        outputStream.write(message);
        outputStream.flush();
        outputStream.close();
    }
}
