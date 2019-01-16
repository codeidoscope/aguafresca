package com.github.codeidoscope;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInputValidatorTest {

    @BeforeEach
    void setUp() {
        Configuration.getInstance().setPortNumber(8080);
        Configuration.getInstance().setDirectoryPath(System.getProperty("user.dir"));
        Configuration.getInstance().setSubPath("");
    }

    @Test
    void setsPortAndDirectoryToDefaultValuesWhenUserInputIsIncorrect() {
        UserInputValidator userInputValidator = new UserInputValidator();
        String[] arguments = {"--directory", "1234", "--port", "/testdir"};
        String userDirectory = System.getProperty("user.dir");

        userInputValidator.validate(arguments);
        assertEquals(8080, Configuration.getInstance().getPortNumber());
        assertEquals(userDirectory, Configuration.getInstance().getDirectoryPath());
    }

    @Test
    void setsPortAndDirectoryAccordingToUserInputWhenInputIsCorrect() {
        UserInputValidator userInputValidator = new UserInputValidator();
        String[] arguments = {"--port", "1234", "--directory", "/testdir"};

        userInputValidator.validate(arguments);

        assertEquals(1234, Configuration.getInstance().getPortNumber());
        assertEquals("/testdir", Configuration.getInstance().getSubPath());
    }

    @Test
    void parsesDirectoryGivenByUserInArgumentsToExtractSubdirectory() {
        UserInputValidator userInputValidator = new UserInputValidator();
        String[] arguments = {"--port", "1234", "--directory", "./testdir"};

        userInputValidator.validate(arguments);
        assertEquals(1234, Configuration.getInstance().getPortNumber());
        assertEquals("/testdir", Configuration.getInstance().getSubPath());
    }

    @Test
    void parsesDirectoryGivenByUserInArgumentsToExtractSubdirectory2() {
        UserInputValidator userInputValidator = new UserInputValidator();
        String path = "./testdir";

        String parsedPath = userInputValidator.parseDirectoryPath(path);
        Configuration.getInstance().setSubPath(parsedPath);
        assertEquals("/testdir", Configuration.getInstance().getSubPath());
    }
}
