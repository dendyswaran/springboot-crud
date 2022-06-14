package com.deloitte.baseapp.modules.teams.payloads.response;

import com.deloitte.baseapp.commons.GenericResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class OrgTeamResponse extends GenericResponse {
    private String orgName;
    private UUID orgId;
    private String orgCode;
}
