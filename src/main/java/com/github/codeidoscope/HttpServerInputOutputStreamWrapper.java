package com.github.codeidoscope;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class HttpServerInputOutputStreamWrapper implements InputOutputStreamWrapper {
    @Override
    public String getInput(InputStream inputStream) throws IOException {
        return new BufferedReader(new InputStreamReader(inputStream)).readLine();
    }

    @Override
    public void sendOutput(OutputStream outputStream, byte[] message) throws IOException {
        new DataOutputStream(outputStream).write(message);
    }
}
