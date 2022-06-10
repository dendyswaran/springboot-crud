package com.deloitte.baseapp.modules.authentication.payloads;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@ToString
public class SignInOrgUserRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
