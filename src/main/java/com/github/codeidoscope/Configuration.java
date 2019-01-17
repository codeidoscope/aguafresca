package com.github.codeidoscope;

class Configuration {

    private static Configuration INSTANCE;
    private String contentRootPath = System.getProperty("user.dir");
    private int portNumber = 8080;

    static Configuration getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Configuration();
        }

        return INSTANCE;
    }

    void setContentRootPath(String contentRootPath) {
        this.contentRootPath = contentRootPath;
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
