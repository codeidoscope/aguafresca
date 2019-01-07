package com.github.codeidoscope;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerRunner {
    private ServerConnection serverConnection;
    ServerRunner(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    void startServer(int portNumber) {
        ServerLogger.serverLogger.log(Level.INFO, "Connection made to port " + portNumber);
        serverConnection.createServerSocket(portNumber);
        serverConnection.listenForClientConnection();
        try {
            String url = parseRequest(serverConnection.getInput());

            String response;
            if (url.equals("/valid")) {
                response = "HTTP/1.1 200 OK\r\n";
            } else {
                response = "HTTP/1.1 404 Not Found\r\n";
            }

            serverConnection.sendOutput(response);

            serverConnection.closeClientConnection();
        } catch (RuntimeException e) {
            System.err.println(e);
        }
        serverConnection.closeConnection();
    }

    private String parseRequest(String input) {
        var headers = input.split("\n\r\n")[0].split(" ");
        return headers[1];
    }

}
