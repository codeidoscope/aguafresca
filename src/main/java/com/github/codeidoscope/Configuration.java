package com.github.codeidoscope;

class Configuration {

    private static Configuration INSTANCE;
    private String directoryPath = System.getProperty("user.dir");
    private String portNumber = "8080";

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

    void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    String getPortNumber() {
        return portNumber;
    }
}
