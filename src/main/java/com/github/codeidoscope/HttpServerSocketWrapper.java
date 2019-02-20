package com.github.codeidoscope;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class HttpServerSocketWrapper {
    private ServerSocket serverSocket;

    void create(int portNumber) throws IOException {
        serverSocket = new ServerSocket(portNumber, 500);
    }

    Socket accept() throws IOException {
        return serverSocket.accept();
    }

    void close() throws IOException {
        serverSocket.close();
    }
}
