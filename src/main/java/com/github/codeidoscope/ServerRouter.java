package com.github.codeidoscope;

import java.io.File;
import java.io.IOException;

class ServerRouter implements Router {
    @Override
    public Response route(Request request) throws IOException {
        File file = new File(Configuration.getInstance().getContentRootPath() + request.getPath());
        if (file.isFile()) {
            return new FileHandler().respondToRequest(request);
        } else if (file.isDirectory()) {
            return new DirectoryHandler().respondToRequest(request);
        } else {
            return new DefaultHandler().respondToRequest(request);
        }
    }
}