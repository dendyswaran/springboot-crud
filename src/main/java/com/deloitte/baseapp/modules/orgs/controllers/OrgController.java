package com.deloitte.baseapp.modules.orgs.controllers;

import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.commons.TGenericController;
import com.deloitte.baseapp.commons.TGenericRepository;
import com.deloitte.baseapp.modules.account.entities.OrgUser;
import com.deloitte.baseapp.modules.account.services.TOrgUserService;
import com.deloitte.baseapp.modules.orgs.entites.Org;
import com.deloitte.baseapp.modules.orgs.payloads.request.OrgRequest;
import com.deloitte.baseapp.modules.orgs.services.OrgService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/org")
public class OrgController extends TGenericController<Org, UUID> {

    private final OrgService service;
    private final TOrgUserService userService;

    public OrgController(TGenericRepository<Org, UUID> repository, OrgService service, TOrgUserService userService) {
        super(repository, "Org");
        this.service = service;
        this.userService = userService;
    }

    @PostMapping("/create")
    public MessageResponse<?> createOrg(@Valid @RequestBody OrgRequest payload){
        try {
            Org org = new Org();
            org.setName(payload.getOrgName());
            org.setCode(payload.getOrgCode());
            OrgUser user = userService.get(UUID.fromString(
                    new String("da2f8e24-544c-44be-9a4e-30c18671efdc")
            ));
            org.setUser(user);
            System.out.println(org);
            Org resp = service.createOrg(org);
            System.out.println(resp.toString());
            return new MessageResponse<>(resp);
        } catch (Exception e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), 500);
        }
    }


}
