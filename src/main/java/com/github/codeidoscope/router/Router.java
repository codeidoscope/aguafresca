package com.github.codeidoscope.router;

import com.github.codeidoscope.request.Request;
import com.github.codeidoscope.response.Response;

import java.io.IOException;

public interface Router {
    Response route(Request request) throws IOException;
}
