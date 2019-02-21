package com.github.codeidoscope;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MockClientConnection implements ClientConnection {
    private MockServerConnection mockServerConnection;
    private InputStream input;
    private byte[] message;

    @Override
    public InputStream getInput()  {
        return input;
    }

    @Override
    public void sendOutput(byte[] message) {
        this.message = message;
    }

    @Override
    public void closeClientConnection() {
        mockServerConnection.closeConnection();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return null;
    }

    void setServerConnection(MockServerConnection mockServerConnection) {
        this.mockServerConnection = mockServerConnection;
    }

    void setInput(InputStream inputStream){
        this.input = inputStream;
    }

    byte[] sentResponse() {
        return message;
    }
}
