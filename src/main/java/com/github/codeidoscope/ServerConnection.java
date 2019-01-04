package com.github.codeidoscope;

import java.io.IOException;

public interface ServerConnection {
    void createServerSocket(int portNumber);

    void listenForClientConnection();

    String getInput();

    void sendOutput(String message);

    void closeConnection();

    void closeClientConnection();
}
