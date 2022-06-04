package com.deloitte.baseapp.modules.account.controllers;

import com.deloitte.baseapp.commons.TGenericController;
import com.deloitte.baseapp.commons.TGenericRepository;
import com.deloitte.baseapp.modules.account.entities.OrgUser;

import java.util.UUID;

public class TOrgUserController extends TGenericController<OrgUser, UUID> {

    public TOrgUserController(TGenericRepository<OrgUser, UUID> repository, String cacheKey) {
        super(repository, cacheKey);
    }
}
