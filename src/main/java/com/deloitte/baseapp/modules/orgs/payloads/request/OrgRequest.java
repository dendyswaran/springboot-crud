package com.deloitte.baseapp.modules.orgs.payloads.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class OrgRequest {
    @NotBlank
    private String orgName;

    @NotBlank
    private String orgCode;

}
