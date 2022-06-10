package com.deloitte.baseapp.modules.orgs.services;

import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.commons.tModules.TGenericService;
import com.deloitte.baseapp.modules.orgs.entites.OrgUsrGroup;
import com.deloitte.baseapp.modules.orgs.entites.OrgUsrUsrGroup;
import com.deloitte.baseapp.modules.orgs.repositories.OrgUsrGroupRepository;
import com.deloitte.baseapp.modules.orgs.repositories.OrgUsrUsrGroupRepository;
import com.deloitte.baseapp.modules.tAccount.entities.OrgUser;

import java.util.UUID;

public class OrgUsrUsrGroupService extends TGenericService<OrgUsrUsrGroup, UUID> {
    OrgUsrUsrGroupRepository repository;
    public OrgUsrUsrGroupService(TGenericRepository<OrgUsrUsrGroup, UUID> genericRepository,
                                 OrgUsrUsrGroupRepository repository) {
        super(genericRepository);
        this.repository = repository;
    }

    public OrgUsrUsrGroup createOrgUsrGroupWithUsrAndOrgUsrGroup(
            OrgUser user, OrgUsrGroup orgUsrGroup
    ) {
        OrgUsrUsrGroup orgUsrUsrGroup = new OrgUsrUsrGroup();
        orgUsrUsrGroup.setOrgUser(user);
        orgUsrUsrGroup.setOrgUsrGroup(orgUsrGroup);
        return repository.save(orgUsrUsrGroup);
    }
}

