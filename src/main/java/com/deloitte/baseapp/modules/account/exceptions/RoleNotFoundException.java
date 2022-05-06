package com.deloitte.baseapp.modules.account.exceptions;

public class RoleNotFoundException extends Exception {
    public RoleNotFoundException(final String roleName) {
        super("Role " + roleName + " not found");
    }
}
