package com.github.codeidoscope;

import java.util.Arrays;

class Body {
    private final byte[] body;

    Body(byte[] body) {
        this.body = body;
    }

    byte[] toBytes() {
        return body;
    }

    String getString() {
        return Arrays.toString(body);
    }

    int getLength() {
        return body.length;
    }
}
