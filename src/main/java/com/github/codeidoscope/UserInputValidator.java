package com.github.codeidoscope;

class UserInputValidator {
    void validate(String[] arguments) {
        if (arguments.length == 4) {
            if (arguments[0].equalsIgnoreCase("--port") &&
                    arguments[2].equalsIgnoreCase("--directory")) {
                Configuration.getInstance().setPortNumber(Integer.parseInt(arguments[1]));
                Configuration.getInstance().setContentRootPath(arguments[3]);
            }
        }
    }
}
