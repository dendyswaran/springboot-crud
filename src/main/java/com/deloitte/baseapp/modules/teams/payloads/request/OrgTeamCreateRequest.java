package com.deloitte.baseapp.modules.teams.payloads.request;

import com.deloitte.baseapp.commons.GenericRequestPayload;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class OrgTeamCreateRequest extends GenericRequestPayload {
    @NotBlank
    private String orgId;
}
