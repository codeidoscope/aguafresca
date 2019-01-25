package com.github.codeidoscope;

import java.nio.charset.StandardCharsets;

class Body {
    private final byte[] body;

    Body(byte[] body) {
        this.body = body;
    }

    Body(String body) {
        this.body = body.getBytes();
    }

    byte[] toBytes() {
        return body;
    }

    String getBodyString() {
        return new String(body, StandardCharsets.UTF_8);
    }

    int getLength() {
        return body.length;
    }
}
