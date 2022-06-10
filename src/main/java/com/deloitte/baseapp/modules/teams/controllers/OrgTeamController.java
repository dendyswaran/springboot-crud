package com.deloitte.baseapp.modules.teams.controllers;

import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.commons.ObjectNotFoundException;
import com.deloitte.baseapp.commons.tModules.TGenericController;
import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.modules.teams.entities.OrgTeam;
import com.deloitte.baseapp.modules.teams.payloads.request.OrgTeamCreateRequest;
import com.deloitte.baseapp.modules.teams.payloads.request.OrgTeamUpdateRequest;
import com.deloitte.baseapp.modules.teams.services.OrgTeamService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/org-team")
public class OrgTeamController extends TGenericController<OrgTeam, UUID> {
    private final OrgTeamService service;
    public OrgTeamController(TGenericRepository<OrgTeam, UUID> repository,
                             OrgTeamService orgTeamService) {
        super(repository, "OrgTeam");
        service = orgTeamService;
    }

    @PostMapping("/create")
    public MessageResponse<?> createOrgTeam(@Valid @RequestBody OrgTeamCreateRequest payload) {
        return new MessageResponse<>(service.createOrgTeam(payload));
    }

    @PutMapping("/{id}")
    public MessageResponse<?> editOrgTeam(@PathVariable("id") String orgTeamId, OrgTeamUpdateRequest payload) {
        try {
            return new MessageResponse<>(service.updateOrgTeam(UUID.fromString(orgTeamId), payload));
        } catch (ObjectNotFoundException e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), 500);
        }
    }
}
