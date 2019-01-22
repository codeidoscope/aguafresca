package com.github.codeidoscope;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

class TCPServerConnection implements ServerConnection {
    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    @Override
    public void createServerSocket(int portNumber) throws IOException {
        serverSocket = new ServerSocket(portNumber);
    }

    @Override
    public void listenForClientConnection() throws IOException {
        socket = serverSocket.accept();
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
    }

    @Override
    public String getInput() throws IOException {
        return new BufferedReader(new InputStreamReader(inputStream)).readLine();
    }

    @Override
    public void sendOutput(byte[] message) throws IOException {
        new DataOutputStream(outputStream).write(message);
    }

    @Override
    public void closeConnection() throws IOException {
        ServerLogger.serverLogger.log(Level.INFO, "Closing connection - Bye! \uD83D\uDC4B");
        serverSocket.close();
    }

    @Override
    public void closeClientConnection() throws IOException {
        inputStream.close();
        outputStream.close();
        socket.close();
    }

}
