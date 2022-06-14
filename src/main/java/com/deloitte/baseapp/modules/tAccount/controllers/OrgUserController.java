package com.deloitte.baseapp.modules.tAccount.controllers;

import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.commons.ObjectNotFoundException;
import com.deloitte.baseapp.commons.tModules.TGenericController;
import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.configs.security.services.OrgUserDetailsImpl;
import com.deloitte.baseapp.modules.manageUser.payloads.UserResponse;
import com.deloitte.baseapp.modules.orgs.services.OrgService;
import com.deloitte.baseapp.modules.tAccount.entities.OrgUser;
import com.deloitte.baseapp.modules.tAccount.payloads.request.UpdateUserRequest;
import com.deloitte.baseapp.modules.tAccount.payloads.response.OrgUserResponse;
import com.deloitte.baseapp.modules.tAccount.services.OrgUserService;
import com.deloitte.baseapp.modules.MTStatus.services.MtStatusService;
import com.deloitte.baseapp.modules.teams.entities.OrgUsrTeam;
import com.deloitte.baseapp.modules.teams.payloads.response.OrgUsrTeamResponse;
import com.deloitte.baseapp.modules.teams.services.OrgUsrTeamService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/org-user")
public class OrgUserController extends TGenericController<OrgUser, UUID> {

    private final OrgUserService service;
    private final OrgService orgService;
    private final MtStatusService mtService;
    private final OrgUsrTeamService orgUsrTeamService;
    public OrgUserController(TGenericRepository<OrgUser, UUID> repository,
                             OrgUserService service,
                             OrgService orgService,
                             MtStatusService mtService,
                             OrgUsrTeamService orgUsrTeamService) {
        super(repository, "OrgUserAccount");
        this.service = service;
        this.orgService = orgService;
        this.mtService = mtService;
        this.orgUsrTeamService = orgUsrTeamService;
    }

    // TODO: edit team, edit usrUsrGrp
    @PutMapping("/edit/{id}")
    public MessageResponse<?> updateUser(@PathVariable("id") String id,@Valid @RequestBody UpdateUserRequest payload) {
        try {
            OrgUser user = service.get(UUID.fromString(id));
            user.setName(payload.getName() != null ? payload.getName() : user.getName());
            user.setCode(payload.getCode() != null ? payload.getCode() : user.getCode());
            user.setOrg(payload.getOrgId() != null ?
                    (orgService.checkExistById(UUID.fromString(payload.getOrgId())) ? orgService.get(UUID.fromString(payload.getOrgId())) : user.getOrg())
                    : user.getOrg());
            user.setEmail(payload.getEmail() != null ? payload.getEmail() : user.getEmail());
            user.setMtStatus(payload.getMtStatusId() != null ?
                    (mtService.existById(UUID.fromString(payload.getMtStatusId())) ? mtService.get(UUID.fromString(payload.getMtStatusId())) : user.getMtStatus() )
                    : user.getMtStatus());

            // set the orgUsrTeams
            if(payload.getOrgTeamId() != null) {
                OrgUsrTeam orgUsrTeam = orgUsrTeamService.createUsrTeam(user.getId(), UUID.fromString(payload.getOrgTeamId()));
                Set<OrgUsrTeam> orgUsrTeams = user.getOrgUsrTeams();
                orgUsrTeams.add(orgUsrTeam);
                user.setOrgUsrTeams(orgUsrTeams);
            }
            this.update(user.getId(), user);
            return new MessageResponse<>("Successfully Update User");
        } catch (ObjectNotFoundException e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), e.getCode());
        }catch(Exception e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), 500);
        }
    }
    @GetMapping("/user-list")
    public MessageResponse<?> getUserList() {
        final OrgUserDetailsImpl userPrincipal = (OrgUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<OrgUserResponse> users = service.getAllUsers();
        return new MessageResponse<>(users);
    }

    @GetMapping("/get/{id}")
    public MessageResponse<?> getUser(@PathVariable("id") String id) {
        try{
            return new MessageResponse<>(service.getUser(UUID.fromString(id)));
        } catch (ObjectNotFoundException e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), e.getCode());
        }
    }

}
