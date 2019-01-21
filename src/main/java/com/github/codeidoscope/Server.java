package com.github.codeidoscope;

import java.io.IOException;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerConnection serverConnection = new TCPServerConnection();
        ServerRouter serverRouter = new ServerRouter();
        ServerRunner serverRunner = new HttpServerRunner(serverConnection, serverRouter);
        UserInputValidator userInputValidator = new UserInputValidator();

        userInputValidator.validate(args);
        serverRunner.startServer(Configuration.getInstance().getPortNumber());
    }
}
