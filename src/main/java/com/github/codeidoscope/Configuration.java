package com.github.codeidoscope;

import java.io.File;
import java.io.IOException;

class Configuration {

    private static Configuration INSTANCE;
    private String contentRootPath;
    private int portNumber = 8080;

    static Configuration getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Configuration();
        }

        return INSTANCE;
    }

    Configuration() {
        contentRootPath = makeCanonicalPath(System.getProperty("user.dir"));
    }

    private String makeCanonicalPath(String path) {
        File file = new File(path);
        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't get a file, duh!");
        }
    }

    void setContentRootPath(String contentRootPath) {
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
