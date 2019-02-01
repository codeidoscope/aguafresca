package com.github.codeidoscope;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ServerSocketWrapper {
    void create(int portNumber) throws IOException;
    void accept() throws IOException;
    void close() throws IOException;
    InputStream getInputStream() throws IOException;
    OutputStream getOutputStream() throws IOException;
    void closeClientSocket() throws IOException;
    void closeInputStream() throws IOException;
    void closeOutputStream() throws IOException;
}
