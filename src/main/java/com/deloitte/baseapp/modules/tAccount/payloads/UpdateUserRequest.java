package com.deloitte.baseapp.modules.tAccount.payloads;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class UpdateUserRequest {
    private String name;
    private String code;
    @Email
    private String email;
    private String orgId;
    private String orgUsrGroupId;
    private String mtStatusId;
    private String orgTeamId;
}
