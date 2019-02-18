package com.github.codeidoscope;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ServerRunnerTest {

    @Test
    void testGetValidResourceReturnsOkResponse() throws IOException {
        MockRouter mockServerRouter = new MockRouter();
        mockServerRouter.addRoute("/testdirectory/testfile.txt", new Response(new Header("HTTP/1.1 200 OK"), new Body("Test file.")));
        ByteArrayInputStream input = new ByteArrayInputStream("GET /testdirectory/testfile.txt HTTP/1.1\n\r\n".getBytes());
        byte[] output = "HTTP/1.1 200 OK\n\r\nTest file.".getBytes();
        MockServerConnection mockServerConnection = new MockServerConnection();
        MockClientConnection mockClientConnection = new MockClientConnection();
        mockClientConnection.setInput(input);
        mockServerConnection.setClientConnection(mockClientConnection);
        HttpServerRunner serverRunner = new HttpServerRunner(mockServerConnection, mockServerRouter, Runnable::run);
        mockServerConnection.setServerRunner(serverRunner);
        serverRunner.startServer(8080);

        assertEquals(Arrays.toString(output), Arrays.toString(mockServerConnection.sentResponse()));
    }

    @Test
    void testGetInvalidResourceReturnsNotFoundResponse() throws IOException {
        MockRouter mockServerRouter = new MockRouter();
        mockServerRouter.addRoute("/", new Response(new Header("HTTP/1.1 404 Not Found"), new Body("404 Not Found")));
        ByteArrayInputStream input = new ByteArrayInputStream("GET / HTTP/1.1\n\r\n".getBytes());
        byte[] output = "HTTP/1.1 404 Not Found\n\r\n404 Not Found".getBytes();
        MockServerConnection mockServerConnection = new MockServerConnection();
        MockClientConnection mockClientConnection = new MockClientConnection();
        mockClientConnection.setInput(input);
        mockServerConnection.setClientConnection(mockClientConnection);
        HttpServerRunner serverRunner = new HttpServerRunner(mockServerConnection, mockServerRouter, Runnable::run);
        mockServerConnection.setServerRunner(serverRunner);
        serverRunner.startServer(8080);

        assertEquals(Arrays.toString(output), Arrays.toString(mockServerConnection.sentResponse()));
    }
}