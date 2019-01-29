package com.github.codeidoscope;

import java.io.IOException;

public class Server {

    public static void main(String[] args) throws IOException {
        Configuration.getInstance().validateArgsAndSetParams(args);
        new HttpServerRunner().startServer(Configuration.getInstance().getPortNumber());
    }
}
