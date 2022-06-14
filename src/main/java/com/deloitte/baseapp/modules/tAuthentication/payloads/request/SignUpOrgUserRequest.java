package com.deloitte.baseapp.modules.tAuthentication.payloads.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignUpOrgUserRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

    private String orgUserGroupId;
    private String orgTeamId;
    private String orgId;
}
