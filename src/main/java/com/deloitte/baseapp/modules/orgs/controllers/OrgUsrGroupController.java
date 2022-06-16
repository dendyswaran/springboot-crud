package com.deloitte.baseapp.modules.orgs.controllers;

import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.commons.ObjectNotFoundException;
import com.deloitte.baseapp.commons.tModules.TGenericController;
import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.modules.orgs.entites.OrgUsrGroup;
import com.deloitte.baseapp.modules.orgs.payloads.OrgUsrGroupRequest;
import com.deloitte.baseapp.modules.orgs.services.OrgUsrGroupService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
        return new MessageResponse<>(orgUsrGroup);
    }

    @GetMapping("/org/{id}")
    public MessageResponse<?> getOegUsrGroupBasedOnOrg(@PathVariable("id") String orgId) {
        try {
            List<OrgUsrGroup> orgUsrGroups = service.getOrgUsrGroupBasedOnOrgId(orgId);
            System.out.println(orgUsrGroups);
            return new MessageResponse<>(orgUsrGroups);
        } catch (ObjectNotFoundException e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), e.getCode());
        }
    }
}
