package com.github.codeidoscope;

import java.util.Arrays;

class Header {
    private final byte[] header;

    Header(byte[] header) {
        this.header = header;
    }

    Header(String header) {
        this.header = header.getBytes();
    }

    byte[] toBytes() {
        return header;
    }

    String getHeaderString() {
        return Arrays.toString(header);
    }
}
