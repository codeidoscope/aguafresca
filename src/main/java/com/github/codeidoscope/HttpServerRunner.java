package com.github.codeidoscope;

import java.util.logging.Level;

class HttpServerRunner implements ServerRunner {
    private final RequestParser requestParser = new RequestParser();
    private final ResponseSerialiser responseSerialiser = new ResponseSerialiser();
    private Router serverRouter;
    private ServerConnection serverConnection;
    private boolean serverShouldContinueRunning = true;

    HttpServerRunner(ServerConnection serverConnection, Router serverRouter) {
        this.serverConnection = serverConnection;
        this.serverRouter = serverRouter;
    }

    @Override
    public void startServer(int portNumber) {
        ServerLogger.serverLogger.log(Level.INFO, "Connection made to port " + portNumber);
        serverConnection.createServerSocket(portNumber);
        while (serverShouldContinueRunning) {
            serverConnection.listenForClientConnection();
            try {
                String input = serverConnection.getInput();
                if (input != null) {
                    Request request = requestParser.parse(input);
                    Response response = serverRouter.route(request);
                    String serialisedResponse = responseSerialiser.serialise(response);

                    serverConnection.sendOutput(serialisedResponse);

                    serverConnection.closeClientConnection();
                }
                serverConnection.closeClientConnection();
            } catch (RuntimeException e) {
                System.err.println(e);
            }
        }
        serverConnection.closeConnection();
    }

}
