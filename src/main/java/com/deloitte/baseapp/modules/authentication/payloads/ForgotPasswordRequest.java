package com.deloitte.baseapp.modules.authentication.payloads;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class ForgotPasswordRequest {
    @NotBlank
    @Email
    private String email;
}
