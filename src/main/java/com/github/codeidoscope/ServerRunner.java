package com.github.codeidoscope;

import java.util.logging.Level;

class ServerRunner {
    private final RequestParser requestParser = new RequestParser();
    private final Router router = new Router();
    private ServerConnection serverConnection;
    ServerRunner(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    void startServer(int portNumber) {
        ServerLogger.serverLogger.log(Level.INFO, "Connection made to port " + portNumber);
        serverConnection.createServerSocket(portNumber);
        serverConnection.listenForClientConnection();
            try {
                Request request = requestParser.parse(serverConnection.getInput());
                String response = router.route(request);

                serverConnection.sendOutput(response);

                serverConnection.closeClientConnection();
            } catch (RuntimeException e) {
                System.err.println(e);
            }
        serverConnection.closeConnection();
    }

}
