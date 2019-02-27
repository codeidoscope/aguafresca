package com.github.codeidoscope;

import java.net.http.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class ServerRunnerTest {
    private HttpServerRunner serverRunner;
    private Thread serverRunnerThread;
    private int port = 8080;
    private HttpClient client;

    @BeforeEach
    void setUp() throws IOException {
        Configuration.getInstance().setPortNumber(port);
        Configuration.getInstance().setContentRootPath(System.getProperty("user.dir"));

        serverRunner = new HttpServerRunner();

        this.serverRunnerThread = new Thread(() -> {
            try {
                serverRunner.startServer(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        serverRunnerThread.start();
    }

    @AfterEach
    void tearDown() throws InterruptedException, IOException {
        serverRunner.stopServer();
        serverRunnerThread.join();
    }

    @Test
    void validRequestReturnsValidResponse() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:" + port + "/testdirectory/testfile.txt")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals("Test file.", response.body());
    }

    @Test
    void invalidRequestReturnsNotFoundResponse() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:" + port + "/testdirectory/doesnotexist.txt")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(404, response.statusCode());
        assertEquals("404 - NOT FOUND", response.body());
    }
}
