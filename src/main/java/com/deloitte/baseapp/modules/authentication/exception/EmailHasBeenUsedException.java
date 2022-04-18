package com.deloitte.baseapp.modules.authentication.exception;

public class EmailHasBeenUsedException extends Exception {

    public EmailHasBeenUsedException() {
        super("Email is already in use");
    }
}
