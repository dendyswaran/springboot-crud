package com.deloitte.baseapp.modules.orgs.services;

import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.commons.tModules.TGenericService;
import com.deloitte.baseapp.modules.orgs.entites.OrgUsrGroup;
import com.deloitte.baseapp.modules.orgs.payloads.request.OrgUsrGroupRequest;
import com.deloitte.baseapp.modules.orgs.payloads.response.OrgUsrGroupResponse;
import com.deloitte.baseapp.modules.orgs.repositories.OrgRepository;
import com.deloitte.baseapp.modules.orgs.repositories.OrgUsrGroupRepository;
import org.springframework.stereotype.Service;

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

    public static OrgUsrGroupResponse getOrgUsrGroupResponse(OrgUsrGroup source) {
        if(source == null) {
            return null;
        }
        OrgUsrGroupResponse resp = new OrgUsrGroupResponse();
        resp.setCode(source.getCode());
        resp.setOrgId(source.getOrg().getId());
        resp.setName(source.getName());
        resp.setId(source.getId());
        resp.setOrgName(source.getOrg().getName());
        resp.setOrgCode(source.getOrg().getCode());
        return resp;
    }

}
