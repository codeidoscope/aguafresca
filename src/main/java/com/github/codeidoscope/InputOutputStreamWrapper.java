package com.github.codeidoscope;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface InputOutputStreamWrapper {
    String getInput(InputStream inputStream) throws IOException;
    void sendOutput(OutputStream outputStream, byte[] message) throws IOException;
}
