package com.deloitte.baseapp.modules.authentication.payloads;

import lombok.Data;

@Data
public class SigninRequest {
    private String username;
    private String password;
}
