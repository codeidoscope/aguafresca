package com.github.codeidoscope;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class ResponseSender {
    void send(OutputStream outputStream, Response response) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

        outputStream.write(response.getHeadersToBytes());
        outputStream.write("\r\n".getBytes());

        String contentType = response.getContentType();
        if (contentType.equalsIgnoreCase("text/plain") || contentType.equalsIgnoreCase("text/html")) {
            outputStream.write(response.getBody().readAllBytes());
        } else {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(response.getBody());

            byte[] buffer = new byte[1024];
            int available = -1;
            while ((available = bufferedInputStream.read(buffer)) > 0) {
                bufferedOutputStream.write(buffer, 0, available);
                bufferedOutputStream.flush();
            }
        }
        outputStream.flush();
    }
}
