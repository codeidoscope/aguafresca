package com.github.codeidoscope;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInputValidatorTest {

    @BeforeEach
    void setUp() {
        Configuration.getInstance().setPortNumber(8080);
        Configuration.getInstance().setContentRootPath(System.getProperty("user.dir"));
    }

    @Test
    void setsPortAndDirectoryToDefaultValuesWhenUserInputIsIncorrect() {
        UserInputValidator userInputValidator = new UserInputValidator();
        String[] arguments = {"--directory", "1234", "--port", "/testdir"};
        String userDirectory = System.getProperty("user.dir");

        userInputValidator.validate(arguments);
        assertEquals(8080, Configuration.getInstance().getPortNumber());
        assertEquals(userDirectory, Configuration.getInstance().getContentRootPath());
    }

    @Test
    void setsPortAndDirectoryAccordingToUserInputWhenInputIsCorrect() {
        UserInputValidator userInputValidator = new UserInputValidator();
        String[] arguments = {"--port", "1234", "--directory", "/testdir"};

        userInputValidator.validate(arguments);

        assertEquals(1234, Configuration.getInstance().getPortNumber());
        assertEquals("/testdir", Configuration.getInstance().getContentRootPath());
    }
}
