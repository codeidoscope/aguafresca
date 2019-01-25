package com.github.codeidoscope;

import java.nio.charset.StandardCharsets;

class Header {
    private final byte[] header;

    Header(String header) {
        this.header = header.getBytes();
    }

    byte[] toBytes() {
        return header;
    }

    String getHeaderString() {
        return new String(header, StandardCharsets.UTF_8);
    }
}
