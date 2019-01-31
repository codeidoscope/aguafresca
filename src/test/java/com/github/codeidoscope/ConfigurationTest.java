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

        assertEquals(portNumber, configuration.getPortNumber());
    }

    @Test
    void returns8080AsTheDefaultPortWhenNoneIsSpecified() {
        int defaultPort = 8080;

        assertEquals(defaultPort, configuration.getPortNumber());
    }

    @Test
    void testFromArgs() throws IOException {
        String[] arguments = {"--port", "1234", "--directory", "/testdir"};
        int defaultPort = 1234;
        String contentRootPath = "/testdir";
        Configuration.getInstance().validateArgsAndSetParams(arguments);

        assertEquals(defaultPort, Configuration.getInstance().getPortNumber());
        assertEquals(contentRootPath, Configuration.getInstance().getContentRootPath());
    }
}
