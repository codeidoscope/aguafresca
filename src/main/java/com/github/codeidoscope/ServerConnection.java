package com.github.codeidoscope;


import java.io.IOException;
import java.io.InputStream;

public interface ServerConnection {
    void createServerSocket(int portNumber) throws IOException;

    void listenForClientConnection() throws IOException;

    InputStream getInput() throws IOException;

    void sendOutput(byte[] message) throws IOException;

    void closeConnection() throws IOException;

    void closeClientConnection() throws IOException;
}
