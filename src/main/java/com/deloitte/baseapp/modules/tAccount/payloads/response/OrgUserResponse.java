package com.deloitte.baseapp.modules.tAccount.payloads.response;

import com.deloitte.baseapp.commons.GenericResponse;
import com.deloitte.baseapp.modules.MTStatus.entities.MtStatus;
import com.deloitte.baseapp.modules.orgs.entites.OrgUsrGroup;
import com.deloitte.baseapp.modules.orgs.payloads.response.OrgResponse;
import com.deloitte.baseapp.modules.orgs.payloads.response.OrgUsrGroupResponse;
import com.deloitte.baseapp.modules.teams.entities.OrgTeam;
import com.deloitte.baseapp.modules.teams.payloads.response.OrgTeamResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrgUserResponse extends GenericResponse {
    private String email;
    private List<OrgUsrGroupResponse> orgUsrGroups;
    private List<OrgTeamResponse> orgTeams;
    private MtStatus mtStatus;
    private OrgResponse org;
}
