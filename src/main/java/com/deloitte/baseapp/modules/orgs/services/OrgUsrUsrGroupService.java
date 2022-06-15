package com.deloitte.baseapp.modules.orgs.services;

import com.deloitte.baseapp.commons.ObjectNotFoundException;
import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.commons.tModules.TGenericService;
import com.deloitte.baseapp.modules.orgs.entites.OrgUsrGroup;
import com.deloitte.baseapp.modules.orgs.entites.OrgUsrUsrGroup;
import com.deloitte.baseapp.modules.orgs.repositories.OrgUsrGroupRepository;
import com.deloitte.baseapp.modules.orgs.repositories.OrgUsrUsrGroupRepository;
import com.deloitte.baseapp.modules.tAccount.entities.OrgUser;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrgUsrUsrGroupService extends TGenericService<OrgUsrUsrGroup, UUID> {
    OrgUsrUsrGroupRepository repository;
    OrgUsrGroupRepository orgUsrGroupRepository;
    public OrgUsrUsrGroupService(TGenericRepository<OrgUsrUsrGroup, UUID> genericRepository,
                                 OrgUsrUsrGroupRepository repository,
                                 OrgUsrGroupRepository orgUsrGroupRepository) {
        super(genericRepository);
        this.repository = repository;
        this.orgUsrGroupRepository = orgUsrGroupRepository;
    }

    public OrgUsrUsrGroup createOrgUsrGroupWithUsrAndOrgUsrGroup(
            OrgUser user, OrgUsrGroup orgUsrGroup
    ) {
        OrgUsrUsrGroup orgUsrUsrGroup = new OrgUsrUsrGroup();
        orgUsrUsrGroup.setOrgUser(user);
        orgUsrUsrGroup.setOrgUsrGroup(orgUsrGroup);
        return repository.save(orgUsrUsrGroup);
    }

    public OrgUsrUsrGroup createOrgUsrUsrGroup (OrgUser user, UUID orgUsrGroupId) throws ObjectNotFoundException {
        OrgUsrUsrGroup newInstance = new OrgUsrUsrGroup();
        newInstance.setOrgUsrGroup(orgUsrGroupRepository.findById(orgUsrGroupId).orElseThrow(ObjectNotFoundException::new));
        newInstance.setOrgUser(user);
        return repository.save(newInstance);
    }
}

