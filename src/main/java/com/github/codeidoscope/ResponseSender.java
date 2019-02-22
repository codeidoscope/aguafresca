package com.github.codeidoscope;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

class ResponseSender {
    void send(OutputStream outputStream, Response response) throws IOException {
        outputStream.write(response.getHeadersToBytes());
        outputStream.write("\r\n".getBytes());

        BufferedInputStream bufferedInputStream = new BufferedInputStream(response.getBody());

        byte[] buffer = new byte[1024];
        int available = -1;
        while ((available = bufferedInputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, available);
        }
        outputStream.flush();
    }
}
