package com.github.codeidoscope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

class TCPServerConnection implements ServerConnection {
    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    @Override
    public void createServerSocket(int portNumber) {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Override
    public void listenForClientConnection() {
        try {
            socket = serverSocket.accept();
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Override
    public String getInput() {
        try {
            return new BufferedReader(new InputStreamReader(inputStream)).readLine();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void sendOutput(String message) {
        new PrintWriter(outputStream, true).println(message);
    }

    @Override
    public void closeConnection() {
        ServerLogger.serverLogger.log(Level.INFO, "Closing connection - Bye! \uD83D\uDC4B");
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Override
    public void closeClientConnection() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
