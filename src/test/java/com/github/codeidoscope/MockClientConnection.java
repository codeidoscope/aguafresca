package com.github.codeidoscope;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MockClientConnection implements ClientConnection {
    private MockServerConnection mockServerConnection;
    private InputStream input;
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Override
    public InputStream getInput() {
        return input;
    }

    @Override
    public void closeClientConnection() {
        mockServerConnection.closeConnection();
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    void setServerConnection(MockServerConnection mockServerConnection) {
        this.mockServerConnection = mockServerConnection;
    }

    void setInput(InputStream inputStream) {
        this.input = inputStream;
    }

    byte[] sentResponse() {
        return outputStream.toByteArray();
    }
}
