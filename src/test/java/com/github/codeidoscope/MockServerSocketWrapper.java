package com.github.codeidoscope;

import java.io.InputStream;
import java.io.OutputStream;

public class MockServerSocketWrapper implements ServerSocketWrapper {
    @Override
    public void create(int portNumber) {

    }

    @Override
    public void accept() {

    }

    @Override
    public void close() {

    }

    @Override
    public InputStream getInputStream() {
        return null;
    }

    @Override
    public OutputStream getOutputStream() {
        return null;
    }

    @Override
    public void closeClientSocket() {

    }

    @Override
    public void closeInputStream() {

    }

    @Override
    public void closeOutputStream() {

    }
}
