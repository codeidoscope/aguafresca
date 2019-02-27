package com.github.codeidoscope;

import com.github.codeidoscope.request.Request;
import com.github.codeidoscope.request.RequestParser;
import com.github.codeidoscope.response.Response;
import com.github.codeidoscope.response.ResponseSender;
import com.github.codeidoscope.router.Router;
import com.github.codeidoscope.router.ServerRouter;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;

class HttpServerRunner {
    private final RequestParser requestParser = new RequestParser();
    private Router serverRouter;
    private ServerConnection serverConnection;
    private boolean serverShouldContinueRunning = true;
    private Executor executor;

    HttpServerRunner() throws IOException {
        this.serverConnection = new ServerConnection();
        this.serverRouter = new ServerRouter();
        this.executor = Executors.newCachedThreadPool();
    }

    void startServer(int portNumber) throws IOException {
        ServerLogger.serverLogger.log(Level.INFO, "Connection made to port " + portNumber);
        serverConnection.createServerSocket(portNumber);
        while (serverShouldContinueRunning) {
            try {
                executor.execute(new ServerRunnable(serverConnection.acceptClientConnection()));
            } catch (IOException e) {
                ServerLogger.serverLogger.log(Level.INFO, "Error: " + e);
            }
        }
        serverConnection.closeConnection();
    }

    void stopServer() throws IOException {
        serverShouldContinueRunning = false;
        serverConnection.closeConnection();
    }

    private class ServerRunnable implements Runnable {

        private ResponseSender responseSender = new ResponseSender();
        private Socket socket;

        ServerRunnable(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream input = socket.getInputStream();
                if (input != null) {
                    Request request = requestParser.parse(input);
                    Response response = serverRouter.route(request);

                    responseSender.send(socket.getOutputStream(), response);
                }
                socket.close();
            } catch (IOException e) {
                ServerLogger.serverLogger.log(Level.WARNING, "Error: " + e);
            }
        }
    }
}
