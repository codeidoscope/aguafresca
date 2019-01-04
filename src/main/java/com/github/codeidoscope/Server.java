package com.github.codeidoscope;

public class Server {

    public static void main(String[] args) {
        ServerConnection serverConnection = new TCPServerConnection();
        ServerRunner serverRunner = new ServerRunner(serverConnection);

        int portNumber = Integer.parseInt(args[1]);
        serverRunner.startServer(portNumber);
    }
}
