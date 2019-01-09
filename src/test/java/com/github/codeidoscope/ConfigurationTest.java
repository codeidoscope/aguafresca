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
        configuration.setDirectoryPath(directoryPath);

        assertEquals(configuration.getDirectoryPath(), directoryPath);
    }

    @Test
    void setsTheGivenPortNumberAsPortNumber() {
        String portNumber = "4000";
        configuration.setPortNumber(portNumber);

        assertEquals(configuration.getPortNumber(), portNumber);
    }

    @Test
    void returns8080AsTheDefaultPortWhenNoneIsSpecified() {
        String defaultPort = "8080";
        assertEquals(configuration.getPortNumber(), defaultPort);
    }
}
