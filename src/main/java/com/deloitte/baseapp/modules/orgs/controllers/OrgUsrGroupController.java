package com.deloitte.baseapp.modules.orgs.controllers;

import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.commons.tModules.TGenericController;
import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.modules.orgs.entites.OrgUsrGroup;
import com.deloitte.baseapp.modules.orgs.payloads.request.OrgUsrGroupRequest;
import com.deloitte.baseapp.modules.orgs.payloads.response.OrgUsrGroupResponse;
import com.deloitte.baseapp.modules.orgs.services.OrgUsrGroupService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/org-group")
public class OrgUsrGroupController extends TGenericController<OrgUsrGroup, UUID> {
    private final OrgUsrGroupService service;

    public OrgUsrGroupController(TGenericRepository<OrgUsrGroup, UUID> repository, OrgUsrGroupService service) {
        super(repository, "OrgUsrGroup");
        this.service = service;
    }

    @PostMapping("/create")
    public MessageResponse<?> createOegUsrGrp(@Valid @RequestBody OrgUsrGroupRequest payload) {
        OrgUsrGroup orgUsrGroup = service.createOrgUsrGroup(payload);
        OrgUsrGroupResponse resp = new OrgUsrGroupResponse();
        resp.setCode(orgUsrGroup.getCode());
        resp.setOrgId(orgUsrGroup.getOrg().getId());
        resp.setName(orgUsrGroup.getName());
        resp.setId(orgUsrGroup.getId());
        return new MessageResponse<>(resp);
    }


}