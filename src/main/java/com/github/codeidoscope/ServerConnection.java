package com.github.codeidoscope;


import java.io.IOException;

public interface ServerConnection {
    void createServerSocket(int portNumber) throws IOException;

    ClientConnection acceptClientConnection() throws IOException;

    void closeConnection() throws IOException;

}
