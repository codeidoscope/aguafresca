package com.github.codeidoscope;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServerSocketWrapper {
    private ServerSocket serverSocket;

    public void create(int portNumber) throws IOException {
        serverSocket = new ServerSocket(portNumber);
    }

    public Socket accept() throws IOException {
        return serverSocket.accept();
    }

    public void close() throws IOException {
        serverSocket.close();
    }
}
