package com.github.codeidoscope;

import java.io.IOException;
import java.util.logging.Level;

class TCPServerConnection implements ServerConnection {
    private HttpServerSocketWrapper serverSocket;

    TCPServerConnection(HttpServerSocketWrapper serverSocketWrapper) {
        this.serverSocket = serverSocketWrapper;
    }

    @Override
    public void createServerSocket(int portNumber) throws IOException {
        serverSocket.create(portNumber);
    }


    @Override
    public ClientConnection acceptClientConnection() throws IOException {
        return new TCPClientConnection(serverSocket.accept());
    }

    @Override
    public void closeConnection() throws IOException {
        ServerLogger.serverLogger.log(Level.INFO, "Closing connection - Bye! \uD83D\uDC4B");
        serverSocket.close();
    }

}
