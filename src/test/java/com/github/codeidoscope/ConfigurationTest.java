package com.github.codeidoscope;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {

    private Configuration configuration;

    @BeforeEach
    void setUp() throws IOException {
        configuration = new Configuration();
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
