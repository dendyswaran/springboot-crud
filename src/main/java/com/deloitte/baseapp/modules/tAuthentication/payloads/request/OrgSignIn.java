package com.deloitte.baseapp.modules.tAuthentication.payloads.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class OrgSignIn {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
