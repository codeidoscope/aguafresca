package com.github.codeidoscope;

import java.io.InputStream;

class Body {
    private InputStream body;

    Body(InputStream body) {
        this.body = body;
    }

    InputStream getBody() {
        return body;
    }

    String getBodyString() {
        return String.valueOf(body);
    }
}
