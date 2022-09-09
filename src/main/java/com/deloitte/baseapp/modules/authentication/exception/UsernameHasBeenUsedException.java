package com.deloitte.baseapp.modules.authentication.exception;

public class UsernameHasBeenUsedException extends Exception {

    public UsernameHasBeenUsedException() {
        super("Username is already in use");
    }
}
