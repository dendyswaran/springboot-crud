package com.deloitte.baseapp.commons;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjectNotFoundException extends Exception {

    private int code = 400;

    public ObjectNotFoundException() {
        super("Object not found");
    }
}
