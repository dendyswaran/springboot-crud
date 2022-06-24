package com.deloitte.baseapp.modules.authentication.exception;

public class AlreadyLoggedOutException extends Exception {

    public AlreadyLoggedOutException() { super("User already logged out");
    }

}
