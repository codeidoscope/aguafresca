package com.github.codeidoscope;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ServerRunnerTest {

    @Test
    void testGetValidResourceReturnsOkResponse() throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream("GET /testdirectory/testfile.txt HTTP/1.1\r\n".getBytes());
        MockClientConnection mockClientConnection = new MockClientConnection();
        mockClientConnection.setInput(input);

        MockServerConnection mockServerConnection = new MockServerConnection();
        mockServerConnection.setClientConnection(mockClientConnection);

        MockRouter mockServerRouter = new MockRouter();
        mockServerRouter.addRoute("/testdirectory/testfile.txt", new Response(new Header("HTTP/1.1 200 OK"), new Body(new ByteArrayInputStream("Test file.".getBytes())), "text/plain"));

        HttpServerRunner serverRunner = new HttpServerRunner(mockServerConnection, mockServerRouter, Runnable::run);
        mockServerConnection.setServerRunner(serverRunner);

        serverRunner.startServer(8080);

        byte[] output = "HTTP/1.1 200 OK\r\nTest file.".getBytes();

        assertEquals(new String(output), new String(mockServerConnection.sentResponse()));
    }

    @Test
    void testGetInvalidResourceReturnsNotFoundResponse() throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream("GET / HTTP/1.1\r\n".getBytes());
        MockClientConnection mockClientConnection = new MockClientConnection();
        mockClientConnection.setInput(input);

        MockServerConnection mockServerConnection = new MockServerConnection();
        mockServerConnection.setClientConnection(mockClientConnection);

        MockRouter mockServerRouter = new MockRouter();
        mockServerRouter.addRoute("/", new Response(new Header("HTTP/1.1 404 Not Found"), new Body(new ByteArrayInputStream("404 Not Found".getBytes())), "text/plain"));

        HttpServerRunner serverRunner = new HttpServerRunner(mockServerConnection, mockServerRouter, Runnable::run);
        mockServerConnection.setServerRunner(serverRunner);

        serverRunner.startServer(8080);

        byte[] expectedOutput = "HTTP/1.1 404 Not Found\r\n404 Not Found".getBytes();

        assertEquals(new String(expectedOutput), new String(mockServerConnection.sentResponse()));
    }
}