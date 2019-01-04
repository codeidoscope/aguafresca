package com.github.codeidoscope;

public class MockServerConnection implements ServerConnection {
    private String message;
    private String input;

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
    public void sendOutput(String message) {
        this.message = message;
    }

    @Override
    public void closeConnection() {

    }

    @Override
    public void closeClientConnection() {

    }

    public String sentResponse() {
        return message;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
