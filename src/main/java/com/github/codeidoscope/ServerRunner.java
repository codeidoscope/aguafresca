package com.github.codeidoscope;

import java.util.logging.Level;

class ServerRunner {
    private final RequestParser requestParser = new RequestParser();
    private final ResponseSerialiser responseSerialiser = new ResponseSerialiser();
    private Router serverRouter;
    private ServerConnection serverConnection;

    ServerRunner(ServerConnection serverConnection, Router serverRouter) {
        this.serverConnection = serverConnection;
        this.serverRouter = serverRouter;
    }

    void startServer(int portNumber) {
        ServerLogger.serverLogger.log(Level.INFO, "Connection made to port " + portNumber);
        serverConnection.createServerSocket(portNumber);
        serverConnection.listenForClientConnection();
        try {
            Request request = requestParser.parse(serverConnection.getInput());
            Response response = serverRouter.route(request);
            String serialisedResponse = responseSerialiser.serialise(response);

            serverConnection.sendOutput(serialisedResponse);

            serverConnection.closeClientConnection();
        } catch (RuntimeException e) {
            System.err.println(e);
        }
        serverConnection.closeConnection();
    }

}
