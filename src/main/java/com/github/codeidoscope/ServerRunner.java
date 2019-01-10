package com.github.codeidoscope;

import java.util.logging.Level;

class ServerRunner {
    private final RequestParser requestParser = new RequestParser();
    private Router router;
    private ServerConnection serverConnection;

    ServerRunner(ServerConnection serverConnection, Router router) {
        this.serverConnection = serverConnection;
        this.router = router;
    }

    void startServer(int portNumber) {
        ServerLogger.serverLogger.log(Level.INFO, "Connection made to port " + portNumber);
        serverConnection.createServerSocket(portNumber);
        serverConnection.listenForClientConnection();
        try {
            Request request = requestParser.parse(serverConnection.getInput());
            Response response = router.route(request);

//                serverConnection.sendOutput(response);

            serverConnection.closeClientConnection();
        } catch (RuntimeException e) {
            System.err.println(e);
        }
        serverConnection.closeConnection();
    }

}
