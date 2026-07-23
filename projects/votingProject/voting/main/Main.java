package main;

import exception.InputValidationException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;

public class Main {

    public static void main(String[] args) throws UserNotFoundException, UserAlreadyExistsException, InputValidationException {

        ApplicationController controller = new ApplicationController();

        controller.start();
    }

}
