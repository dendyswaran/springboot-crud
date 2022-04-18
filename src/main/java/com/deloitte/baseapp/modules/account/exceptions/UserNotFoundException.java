package com.deloitte.baseapp.modules.account.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("User not found");
    }

}
