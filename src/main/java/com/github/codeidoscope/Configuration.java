package com.github.codeidoscope;

import java.io.File;
import java.io.IOException;

class Configuration {

    private static Configuration INSTANCE;
    private String contentRootPath;
    private int portNumber = 8080;

    static Configuration getInstance() throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new Configuration();
        }

        return INSTANCE;
    }

    Configuration() throws IOException {
        contentRootPath = makeCanonicalPath(System.getProperty("user.dir"));
    }

    private String makeCanonicalPath(String path) throws IOException {
        File file = new File(path);
        return file.getCanonicalPath();
    }

    void setContentRootPath(String contentRootPath) throws IOException {
        this.contentRootPath = makeCanonicalPath(contentRootPath);
    }

    String getContentRootPath() {
        return contentRootPath;
    }

    void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    int getPortNumber() {
        return portNumber;
    }
}
