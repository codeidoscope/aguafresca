package com.github.codeidoscope.handlers;

import com.github.codeidoscope.request.Request;
import com.github.codeidoscope.response.Response;

import java.io.IOException;

public interface RouteHandler {

    Response respondToRequest(Request request) throws IOException;

}
