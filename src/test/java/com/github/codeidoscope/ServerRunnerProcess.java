package com.github.codeidoscope;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ServerRunnerProcess extends Process {
    Process serverRunnerProcess;

    public static ServerRunnerProcess start() throws IOException {
        return new ServerRunnerProcess();
    }

    ServerRunnerProcess() throws IOException {
        serverRunnerProcess = new ProcessBuilder()
                .command("java", "-jar", "build/server.jar.1.0-SNAPSHOT.jar")
                .inheritIO()
                .start();
    }

    @Override
    public OutputStream getOutputStream() {
        return new ByteArrayOutputStream();
    }

    @Override
    public InputStream getInputStream() {
        return null;
    }

    @Override
    public InputStream getErrorStream() {
        return null;
    }

    @Override
    public int waitFor() throws InterruptedException {
        return 0;
    }

    @Override
    public int exitValue() {
        return 0;
    }

    @Override
    public void destroy() {

    }

    public void stop() {
        serverRunnerProcess.destroy();
    }

    public int awaitTermination(int timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return serverRunnerProcess.onExit().get(timeout, unit).exitValue();
    }
}
