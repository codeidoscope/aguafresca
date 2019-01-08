package com.github.codeidoscope;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerRunnerTest {

    @Test
    void testGetValidResourceReturnsCorrectResponse() {
        String input = "GET /valid HTTP/1.1\n\r\n";
        String output = "HTTP/1.1 200 OK\n\r\nHello World";
        MockServerConnection serverConnection = new MockServerConnection();
        serverConnection.setInput(input);
        ServerRunner serverRunner = new ServerRunner(serverConnection);

        serverRunner.startServer(8080);
        assertEquals(output, serverConnection.sentResponse());
    }

    @Test
    void testGetInvalidResourceReturnsCorrectResponse() {
        String input = "GET /invalid HTTP/1.1\n\r\n";
        String output = "HTTP/1.1 404 Not Found\r\n";
        MockServerConnection serverConnection = new MockServerConnection();
        serverConnection.setInput(input);
        ServerRunner serverRunner = new ServerRunner(serverConnection);

        serverRunner.startServer(8080);
        assertEquals(output, serverConnection.sentResponse());
}
}