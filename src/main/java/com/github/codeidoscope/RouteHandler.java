package com.github.codeidoscope;

import java.io.IOException;

interface RouteHandler {

    Response respondToRequest(Request request) throws IOException;

}
