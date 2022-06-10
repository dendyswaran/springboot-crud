package com.deloitte.baseapp.modules.orgs.payloads.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrgUsrGroupResponse {
    private UUID id;
    private String name;
    private String code;
    private UUID orgId;
}
