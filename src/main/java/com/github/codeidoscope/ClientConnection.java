package com.github.codeidoscope;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ClientConnection {
    InputStream getInput() throws IOException;

    void closeClientConnection() throws IOException;

    OutputStream getOutputStream() throws IOException;
}
