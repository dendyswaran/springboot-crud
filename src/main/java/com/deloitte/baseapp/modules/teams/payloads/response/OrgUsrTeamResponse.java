package com.deloitte.baseapp.modules.teams.payloads.response;

import com.deloitte.baseapp.commons.GenericResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class OrgUsrTeamResponse {
    private UUID id;
    private String userName;
    private UUID userId;
    private String orgTeamName;
    private UUID orgTeamId;
}
