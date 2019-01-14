package com.github.codeidoscope;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInputValidatorTest {

    @Test
    void setsPortAndDirectoryAccordingToUserInput() {
        UserInputValidator userInputValidator = new UserInputValidator();
        String[] arguments = {"--port", "1234", "--directory", "/testdir"};

        userInputValidator.validate(arguments);

        assertEquals(Configuration.getInstance().getPortNumber(), 1234);
        assertEquals(Configuration.getInstance().getSubPath(), "/testdir");
    }

    @Test
    void setsPortAndDirectoryToDefaultValuesWhenUserInputIsIncorrect() {
        UserInputValidator userInputValidator = new UserInputValidator();
        String[] arguments = {"--directory", "1234", "--port", "/testdir"};
        String userDirectory = System.getProperty("user.dir");

        userInputValidator.validate(arguments);
        assertEquals(Configuration.getInstance().getPortNumber(), 8080);
        assertEquals(Configuration.getInstance().getDirectoryPath(), userDirectory);
    }
}
