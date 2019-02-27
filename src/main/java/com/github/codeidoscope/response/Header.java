package com.github.codeidoscope.response;

import java.nio.charset.StandardCharsets;

public class Header {
    private final byte[] header;

    public Header(String header) {
        this.header = header.getBytes();
    }

    byte[] toBytes() {
        return header;
    }

    String getHeaderString() {
        return new String(header, StandardCharsets.UTF_8);
    }
}
