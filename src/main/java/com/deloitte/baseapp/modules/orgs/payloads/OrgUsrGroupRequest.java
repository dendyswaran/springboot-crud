package com.deloitte.baseapp.modules.orgs.payloads;

import com.deloitte.baseapp.commons.GenericRequestPayload;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class OrgUsrGroupRequest extends GenericRequestPayload {
    @NotBlank
    private String orgId;
}
