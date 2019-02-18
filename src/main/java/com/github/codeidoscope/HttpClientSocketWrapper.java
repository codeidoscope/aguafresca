package com.github.codeidoscope;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class HttpClientSocketWrapper {

    private Socket socket;

    HttpClientSocketWrapper(Socket socket) {
        this.socket = socket;
    }

    InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    void close() throws IOException {
        socket.close();
    }
}
