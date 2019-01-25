package com.github.codeidoscope;

import java.io.IOException;

public interface Router {
    Response route(Request request) throws IOException;
}
