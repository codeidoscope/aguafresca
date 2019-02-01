package com.github.codeidoscope;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServerSocketWrapper implements ServerSocketWrapper {
    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    @Override
    public void create(int portNumber) throws IOException {
        serverSocket = new ServerSocket(portNumber);
    }

    @Override
    public void accept() throws IOException {
        socket = serverSocket.accept();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        inputStream = socket.getInputStream();
        return inputStream;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        outputStream = socket.getOutputStream();
        return outputStream;
    }

    @Override
    public void closeClientSocket() throws IOException {
        socket.close();
    }

    @Override
    public void closeInputStream() throws IOException {
        inputStream.close();
    }

    @Override
    public void closeOutputStream() throws IOException {
        outputStream.close();
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}
