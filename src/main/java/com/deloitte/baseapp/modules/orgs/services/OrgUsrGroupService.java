package com.deloitte.baseapp.modules.orgs.services;

import com.deloitte.baseapp.commons.ObjectNotFoundException;
import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.commons.tModules.TGenericService;
import com.deloitte.baseapp.modules.orgs.entites.Org;
import com.deloitte.baseapp.modules.orgs.entites.OrgUsrGroup;
import com.deloitte.baseapp.modules.orgs.payloads.OrgUsrGroupRequest;
import com.deloitte.baseapp.modules.orgs.repositories.OrgRepository;
import com.deloitte.baseapp.modules.orgs.repositories.OrgUsrGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class OrgUsrGroupService extends TGenericService<OrgUsrGroup, UUID> {
    OrgUsrGroupRepository repository;
    OrgRepository orgRepository;
    public OrgUsrGroupService(TGenericRepository<OrgUsrGroup, UUID> repository,
                              OrgUsrGroupRepository orgUsrGroupRepository,
                              OrgRepository orgRepository) {
        super(repository);
        this.repository = orgUsrGroupRepository;
        this.orgRepository = orgRepository;
    }

    public OrgUsrGroup createOrgUsrGroup (OrgUsrGroupRequest payload){
        OrgUsrGroup orgUsrGroup = new OrgUsrGroup();
        orgUsrGroup.setOrg(orgRepository.getById(UUID.fromString(payload.getOrgId())));
        orgUsrGroup.setName(payload.getName());
        orgUsrGroup.setCode(payload.getCode());
        return repository.save(orgUsrGroup);
    }

    public List<OrgUsrGroup> getOrgUsrGroupBasedOnOrgId(String id) throws ObjectNotFoundException {
        Org org = orgRepository.findById(UUID.fromString(id)).orElseThrow(ObjectNotFoundException::new);
        return repository.getOrgUsrGroupsByOrg(org);
    }

    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

}
