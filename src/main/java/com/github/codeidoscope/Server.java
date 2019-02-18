package com.github.codeidoscope;

import java.io.IOException;
import java.util.logging.Level;

public class Server {

    public static void main(String[] args) throws IOException {
        Configuration.getInstance().validateArgsAndSetParams(args);
        try {
            new HttpServerRunner().startServer(Configuration.getInstance().getPortNumber());
        } catch (IOException e) {
            ServerLogger.serverLogger.log(Level.WARNING, "Error: " + e);
        }
    }
}