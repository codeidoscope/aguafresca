package com.github.codeidoscope;

import java.io.InputStream;
import java.io.OutputStream;

public class MockInputOutputStreamWrapper implements InputOutputStreamWrapper {
    private byte[] message;
    private InputStream input;

    @Override
    public InputStream getInput(InputStream inputStream) {
        return input;
    }

    @Override
    public void sendOutput(OutputStream outputStream, byte[] message) {
        this.message = message;
    }

    byte[] sentResponse() {
        return message;
    }

    void setInput(InputStream input) {
        this.input = input;
    }
}
