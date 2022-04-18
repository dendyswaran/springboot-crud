package com.deloitte.baseapp.commons;

public class ObjectNotFoundException extends Exception {
    public ObjectNotFoundException() {
        super("Object not found");
    }
}
