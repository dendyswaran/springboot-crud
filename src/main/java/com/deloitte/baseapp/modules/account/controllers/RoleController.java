package com.deloitte.baseapp.modules.account.controllers;

import com.deloitte.baseapp.commons.GenericController;
import com.deloitte.baseapp.commons.GenericRepository;
import com.deloitte.baseapp.modules.account.entities.Role;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RoleController extends GenericController<Role> {

    public RoleController(GenericRepository<Role> repository) {
        super(repository);
    }
}
