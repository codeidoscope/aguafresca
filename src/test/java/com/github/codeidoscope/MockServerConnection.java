package com.github.codeidoscope;

public class MockServerConnection implements ServerConnection {
    private byte[] message;
    private String input;
    private HttpServerRunner serverRunner;

    @Override
    public void createServerSocket(int portNumber) {

    }

    @Override
    public void listenForClientConnection() {

    }

    @Override
    public String getInput() {
        return input;
    }

    @Override
    public void sendOutput(byte[] message) {
        this.message = message;
        stopServerRunner();
    }

    @Override
    public void closeConnection() {

    }

    @Override
    public void closeClientConnection() {

    }

    byte[] sentResponse() {
        return message;
    }

    void setInput(String input) {
        this.input = input;
    }

    void setServerRunner(HttpServerRunner serverRunner){
        this.serverRunner = serverRunner;
    }

    private void stopServerRunner(){
        serverRunner.stopServer();
    }
}
