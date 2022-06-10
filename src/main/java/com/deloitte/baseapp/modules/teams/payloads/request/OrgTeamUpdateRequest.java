package com.deloitte.baseapp.modules.teams.payloads.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrgTeamUpdateRequest {
    private String name;
    private String code;
    private String orgId;
}
