package com.github.codeidoscope;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpServerInputOutputStreamWrapper implements InputOutputStreamWrapper {
    @Override
    public InputStream getInput(InputStream inputStream) {
        return inputStream;
    }

    @Override
    public void sendOutput(OutputStream outputStream, byte[] message) throws IOException {
        outputStream.write(message);
        outputStream.flush();
        outputStream.close();
    }
}
