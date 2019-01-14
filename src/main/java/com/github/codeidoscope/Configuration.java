package com.github.codeidoscope;

class Configuration {

    private static Configuration INSTANCE;
    private String directoryPath = System.getProperty("user.dir");
    private int portNumber = 8080;
    private String subPath = null;

    static Configuration getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Configuration();
        }

        return INSTANCE;
    }

    void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    String getDirectoryPath() {
        return directoryPath;
    }

    void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    int getPortNumber() {
        return portNumber;
    }

    String getSubPath() {
        return subPath;
    }

    void setSubPath(String subPath) {
        this.subPath = subPath;
    }
}
