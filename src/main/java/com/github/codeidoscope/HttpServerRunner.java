package com.github.codeidoscope;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;

class HttpServerRunner {
    private final RequestParser requestParser = new RequestParser();
    private final ResponseSerialiser responseSerialiser = new ResponseSerialiser();
    private Router serverRouter;
    private ServerConnection serverConnection;
    private boolean serverShouldContinueRunning = true;
    private Executor executor;

    HttpServerRunner() {
        this.serverConnection = new TCPServerConnection(new HttpServerSocketWrapper());
        this.serverRouter = new ServerRouter();
        this.executor = Executors.newCachedThreadPool();
    }

    HttpServerRunner(ServerConnection serverConnection, Router serverRouter, Executor executor) {
        this.serverConnection = serverConnection;
        this.serverRouter = serverRouter;
        this.executor = executor;
    }

    void startServer(int portNumber) throws RuntimeException, IOException {
        ServerLogger.serverLogger.log(Level.INFO, "Connection made to port " + portNumber);
        serverConnection.createServerSocket(portNumber);
        while (serverShouldContinueRunning) {
            executor.execute(new ServerRunnable(serverConnection.acceptClientConnection()));
        }
        serverConnection.closeConnection();
    }

    void stopServer() {
        serverShouldContinueRunning = false;
    }

    private class ServerRunnable implements Runnable {

        private ResponseSender responseSender = new ResponseSender();
        private final ClientConnection TCPClientConnection;

        ServerRunnable(ClientConnection TCPClientConnection) {
            this.TCPClientConnection = TCPClientConnection;
        }

        @Override
        public void run() {
            try {
                InputStream input = TCPClientConnection.getInput();
                if (input != null) {
                    Request request = requestParser.parse(input);
                    Response response = serverRouter.route(request);
                    byte[] serialisedResponse = responseSerialiser.serialise(response);

//                    TCPClientConnection.sendOutput(serialisedResponse);

                    responseSender.send(TCPClientConnection.getOutputStream(), response);
                }
                TCPClientConnection.closeClientConnection();
            } catch (IOException e) {
                ServerLogger.serverLogger.log(Level.WARNING, "Error: " + e);
            }
        }
    }
}
