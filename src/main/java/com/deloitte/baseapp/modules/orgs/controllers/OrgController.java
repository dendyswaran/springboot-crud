package com.deloitte.baseapp.modules.orgs.controllers;

import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.commons.tModules.TGenericController;
import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.modules.tAccount.services.OrgUserService;
import com.deloitte.baseapp.modules.orgs.entites.Org;
import com.deloitte.baseapp.commons.GenericRequestPayload;
import com.deloitte.baseapp.modules.orgs.services.OrgService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/org")
public class OrgController extends TGenericController<Org, UUID> {

    private final OrgService service;
    private final OrgUserService userService;

    public OrgController(TGenericRepository<Org, UUID> repository, OrgService service, OrgUserService userService) {
        super(repository, "Org");
        this.service = service;
        this.userService = userService;
    }

    @PostMapping("/create")
    public MessageResponse<?> createOrg(@Valid @RequestBody GenericRequestPayload payload){
        try {
            Org org = new Org();
            org.setName(payload.getName());
            org.setCode(payload.getCode());
            Org resp = service.createOrg(org);
            System.out.println(resp.toString());
            return new MessageResponse<>(resp);
        } catch (Exception e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), 500);
        }
    }

    @GetMapping("/list")
    public MessageResponse<?> getOrgList() {
        List<Org> orgList = service.getAll();
        return new MessageResponse<>(orgList);
    }

}
