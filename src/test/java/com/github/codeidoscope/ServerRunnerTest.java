package com.github.codeidoscope;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerRunnerTest {

    @Test
    void testGetValidResourceReturnsCorrectResponse() {
        MockRouter mockServerRouter = new MockRouter();
        mockServerRouter.addRoute("/valid", new Response("HTTP/1.1 200 OK", "Hello World"));
        String input = "GET /valid HTTP/1.1\n\r\n";
        String output = "HTTP/1.1 200 OK\n\r\nHello World";
        MockServerConnection serverConnection = new MockServerConnection();
        serverConnection.setInput(input);
        ServerRunner serverRunner = new ServerRunner(serverConnection, mockServerRouter);

        serverRunner.startServer(8080);
        assertEquals(output, serverConnection.sentResponse());
    }

    @Test
    void testGetInvalidResourceReturnsCorrectResponse() {
        MockRouter mockServerRouter = new MockRouter();
        mockServerRouter.addRoute("/", new Response("HTTP/1.1 404 Not Found", "404 Not Found"));
        String input = "GET / HTTP/1.1\n\r\n";
        String output = "HTTP/1.1 404 Not Found\n\r\n404 Not Found";
        MockServerConnection serverConnection = new MockServerConnection();
        serverConnection.setInput(input);
        ServerRunner serverRunner = new ServerRunner(serverConnection, mockServerRouter);

        serverRunner.startServer(8080);
        assertEquals(output, serverConnection.sentResponse());
    }
}