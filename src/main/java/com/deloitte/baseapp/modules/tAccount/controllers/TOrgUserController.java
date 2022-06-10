package com.deloitte.baseapp.modules.tAccount.controllers;

import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.commons.ObjectNotFoundException;
import com.deloitte.baseapp.commons.tModules.TGenericController;
import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.modules.orgs.services.OrgService;
import com.deloitte.baseapp.modules.tAccount.entities.OrgUser;
import com.deloitte.baseapp.modules.tAccount.payloads.request.UpdateUserRequest;
import com.deloitte.baseapp.modules.tAccount.services.OrgUserService;
import com.deloitte.baseapp.modules.MTStatus.services.MtStatusService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/org-user")
public class TOrgUserController extends TGenericController<OrgUser, UUID> {

    private final OrgUserService service;
    private final OrgService orgService;
    private final MtStatusService mtService;
    public TOrgUserController(TGenericRepository<OrgUser, UUID> repository,
                              OrgUserService service,
                              OrgService orgService,
                              MtStatusService mtService) {
        super(repository, "OrgUserAccount");
        this.service = service;
        this.orgService = orgService;
        this.mtService = mtService;
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
            this.update(user.getId(), user);
            return new MessageResponse<>("Successfully Update User");
        } catch (ObjectNotFoundException e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), e.getCode());
        }
    }
}
