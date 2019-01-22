package com.github.codeidoscope;


import java.io.IOException;

public interface ServerConnection {
    void createServerSocket(int portNumber) throws IOException;

    void listenForClientConnection() throws IOException;

    String getInput() throws IOException;

    void sendOutput(byte[] message) throws IOException;

    void closeConnection() throws IOException;

    void closeClientConnection() throws IOException;
}
