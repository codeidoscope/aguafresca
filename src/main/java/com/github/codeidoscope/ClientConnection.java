package com.github.codeidoscope;

import java.io.IOException;
import java.io.InputStream;

public interface ClientConnection {
    InputStream getInput() throws IOException;

    void sendOutput(byte[] message) throws IOException;

    void closeClientConnection() throws IOException;
}
