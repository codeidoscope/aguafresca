package com.github.codeidoscope;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class TCPClientConnection implements ClientConnection {
    private HttpClientSocketWrapper httpClientSocketWrapper;

    TCPClientConnection(Socket socket) {
        this.httpClientSocketWrapper = new HttpClientSocketWrapper(socket);
    }

    @Override
    public InputStream getInput() throws IOException {
        return httpClientSocketWrapper.getInputStream();
    }

    @Override
    public void closeClientConnection() throws IOException {
        httpClientSocketWrapper.close();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return httpClientSocketWrapper.getOutputStream();
    }
}
