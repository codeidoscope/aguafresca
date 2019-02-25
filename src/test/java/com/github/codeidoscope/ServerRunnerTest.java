package com.github.codeidoscope;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

class ServerRunnerTest {
    private ServerRunnerProcess serverRunnerProcess;

    @BeforeEach
    void setUp() throws IOException {
        serverRunnerProcess = ServerRunnerProcess.start();
    }

    @AfterEach
    void tearDown() throws InterruptedException, ExecutionException, TimeoutException {
        serverRunnerProcess.stop();
        serverRunnerProcess.awaitTermination(2, TimeUnit.SECONDS);
    }

    @Test
    void testSomethingWorks() throws IOException {
        OutputStream stdin = serverRunnerProcess.getOutputStream();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

        writer.write("GET localhost:8080/ HTTP/1.1");
        writer.newLine();
        writer.flush();
        writer.close();
    }
}
