package com.deloitte.baseapp.modules.authentication.exception;

public class BadCredentialException extends Exception {

    public BadCredentialException() {
        super("Wrong email or password");
    }

}
