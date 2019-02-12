package com.github.codeidoscope;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

class TCPServerConnection implements ServerConnection {
    private ServerSocketWrapper serverSocket;
    private InputOutputStreamWrapper inputOutputStreamWrapper;
    private InputStream inputStream;
    private OutputStream outputStream;

    TCPServerConnection(ServerSocketWrapper serverSocketWrapper, InputOutputStreamWrapper inputOutputStreamWrapper) {
        this.serverSocket = serverSocketWrapper;
        this.inputOutputStreamWrapper = inputOutputStreamWrapper;
    }

    @Override
    public void createServerSocket(int portNumber) throws IOException {
        serverSocket.create(portNumber);
    }

    @Override
    public void listenForClientConnection() throws IOException {
        serverSocket.accept();
        inputStream = serverSocket.getInputStream();
        outputStream = serverSocket.getOutputStream();
    }

    @Override
    public InputStream getInput() throws IOException {
        return inputOutputStreamWrapper.getInput(inputStream);
    }

    @Override
    public void sendOutput(byte[] message) throws IOException {
        inputOutputStreamWrapper.sendOutput(outputStream, message);
    }

    @Override
    public void closeConnection() throws IOException {
        ServerLogger.serverLogger.log(Level.INFO, "Closing connection - Bye! \uD83D\uDC4B");
        serverSocket.close();
    }

    @Override
    public void closeClientConnection() throws IOException {
        serverSocket.closeInputStream();
        serverSocket.closeOutputStream();
        serverSocket.closeClientSocket();
    }

}
