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
        MockServerConnection serverConnection = new MockServerConnection();
        serverConnection.setInput(input);
        HttpServerRunner serverRunner = new HttpServerRunner(serverConnection, mockServerRouter);
        serverConnection.setServerRunner(serverRunner);
        serverRunner.startServer(8080);

        assertEquals(Arrays.toString(output), Arrays.toString(serverConnection.sentResponse()));
    }

    @Test
    void testGetInvalidResourceReturnsNotFoundResponse() throws IOException {
        MockRouter mockServerRouter = new MockRouter();
        mockServerRouter.addRoute("/", new Response(new Header("HTTP/1.1 404 Not Found"), new Body("404 Not Found")));
        ByteArrayInputStream input = new ByteArrayInputStream("GET / HTTP/1.1\n\r\n".getBytes());
        byte[] output = "HTTP/1.1 404 Not Found\n\r\n404 Not Found".getBytes();
        MockServerConnection serverConnection = new MockServerConnection();
        serverConnection.setInput(input);
        HttpServerRunner serverRunner = new HttpServerRunner(serverConnection, mockServerRouter);
        serverConnection.setServerRunner(serverRunner);
        serverRunner.startServer(8080);

        assertEquals(Arrays.toString(output), Arrays.toString(serverConnection.sentResponse()));
    }

    @Test
    void testGetValidResourceReturnOkResponseWhenTcpConnectionIsUsed() throws IOException {
        MockRouter mockServerRouter = new MockRouter();
        mockServerRouter.addRoute("/testdirectory/testfile.txt", new Response(new Header("HTTP/1.1 200 OK"), new Body("Test file.")));
        ByteArrayInputStream input = new ByteArrayInputStream("GET /testdirectory/testfile.txt HTTP/1.1\n\r\n".getBytes());
        byte[] output = "HTTP/1.1 200 OK\n\r\nTest file.".getBytes();
        MockInputOutputStreamWrapper mockInputOutputStreamWrapper = new MockInputOutputStreamWrapper();
        mockInputOutputStreamWrapper.setInput(input);
        TCPServerConnection serverConnection = new TCPServerConnection(new MockServerSocketWrapper(), mockInputOutputStreamWrapper);
        MockServerRunner mockServerRunner = new MockServerRunner(serverConnection, mockServerRouter);
        mockServerRunner.startServer(8080);

        assertEquals(Arrays.toString(output), Arrays.toString(mockInputOutputStreamWrapper.sentResponse()));
    }
}