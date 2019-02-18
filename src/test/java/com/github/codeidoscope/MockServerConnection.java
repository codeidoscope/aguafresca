package com.github.codeidoscope;

public class MockServerConnection implements ServerConnection {
    private HttpServerRunner serverRunner;
    private MockClientConnection clientConnection;

    @Override
    public void createServerSocket(int portNumber) {

    }

    @Override
    public ClientConnection acceptClientConnection() {
        clientConnection.setServerConnection(this);
        return clientConnection;
    }

    @Override
    public void closeConnection() {
        stopServerRunner();
    }

    byte[] sentResponse() {
        return clientConnection.sentResponse();
    }

    void setServerRunner(HttpServerRunner serverRunner) {
        this.serverRunner = serverRunner;
    }

    private void stopServerRunner() {
        serverRunner.stopServer();
    }

    void setClientConnection(MockClientConnection mockClientConnection) {
        this.clientConnection = mockClientConnection;
    }
}
