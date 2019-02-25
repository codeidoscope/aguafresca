package com.github.codeidoscope;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

class ServerConnection {
    private ServerSocket serverSocket;

    ServerConnection() throws IOException {
        this.serverSocket = new ServerSocket();
    }

    void createServerSocket(int portNumber) throws IOException {
        this.serverSocket = new ServerSocket(portNumber, 500);
    }

    Socket acceptClientConnection() throws IOException {
        return serverSocket.accept();
    }

    void closeConnection() throws IOException {
        ServerLogger.serverLogger.log(Level.INFO, "Closing connection - Bye! \uD83D\uDC4B");
        serverSocket.close();
    }

}
