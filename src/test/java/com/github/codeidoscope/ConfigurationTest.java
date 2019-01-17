package com.github.codeidoscope;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {

    private Configuration configuration;

    @BeforeEach
    void setUp() {
        configuration = new Configuration();
    }

    @Test
    void returnsTheDirectoryPath() {
        String directoryPath = "./public";
        configuration.setContentRootPath(directoryPath);

        assertEquals(configuration.getContentRootPath(), directoryPath);
    }

    @Test
    void setsTheGivenPortNumberAsPortNumber() {
        int portNumber = 4000;
        configuration.setPortNumber(portNumber);

        assertEquals(configuration.getPortNumber(), portNumber);
    }

    @Test
    void returns8080AsTheDefaultPortWhenNoneIsSpecified() {
        int defaultPort = 8080;
        assertEquals(configuration.getPortNumber(), defaultPort);
    }
}
