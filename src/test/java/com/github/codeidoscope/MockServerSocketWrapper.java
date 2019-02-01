package com.github.codeidoscope;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MockServerSocketWrapper implements ServerSocketWrapper {
    @Override
    public void create(int portNumber) throws IOException {

    }

    @Override
    public void accept() throws IOException {

    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public void closeClientSocket() throws IOException {

    }

    @Override
    public void closeInputStream() throws IOException {

    }

    @Override
    public void closeOutputStream() throws IOException {

    }
}
