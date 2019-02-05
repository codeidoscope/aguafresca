package com.github.codeidoscope;

import java.io.IOException;
import java.util.logging.Level;

class HttpServerRunner implements ServerRunner {
    private final RequestParser requestParser = new RequestParser();
    private final ResponseSerialiser responseSerialiser = new ResponseSerialiser();
    private Router serverRouter;
    private ServerConnection serverConnection;
    private boolean serverShouldContinueRunning = true;

    HttpServerRunner() {
        this.serverConnection = new TCPServerConnection(new HttpServerSocketWrapper(), new HttpServerInputOutputStreamWrapper());
        this.serverRouter = new ServerRouter();
    }

    HttpServerRunner(ServerConnection serverConnection, Router serverRouter) {
        this.serverConnection = serverConnection;
        this.serverRouter = serverRouter;
    }

    @Override
    public void startServer(int portNumber) throws RuntimeException, IOException {
        ServerLogger.serverLogger.log(Level.INFO, "Connection made to port " + portNumber);
        serverConnection.createServerSocket(portNumber);
        while (serverShouldContinueRunning) {
            serverConnection.listenForClientConnection();
            try {
            String input = serverConnection.getInput();
            if (input != null) {
                Request request = requestParser.parse(input);
                Response response = serverRouter.route(request);
                byte[] serialisedResponse = responseSerialiser.serialise(response);

                serverConnection.sendOutput(serialisedResponse);
                serverConnection.closeClientConnection();
            }
            serverConnection.closeClientConnection();
            } catch (IOException e) {
                ServerLogger.serverLogger.log(Level.WARNING, "Error: " + e);
            }
        }
        serverConnection.closeConnection();
    }

    void stopServer() {
        serverShouldContinueRunning = false;
    }

}
