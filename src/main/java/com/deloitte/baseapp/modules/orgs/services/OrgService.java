package com.deloitte.baseapp.modules.orgs.services;

import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.commons.tModules.TGenericService;
import com.deloitte.baseapp.modules.orgs.entites.Org;
import com.deloitte.baseapp.modules.orgs.payloads.response.OrgResponse;
import com.deloitte.baseapp.modules.orgs.repositories.OrgRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrgService extends TGenericService<Org, UUID> {
    private final OrgRepository repository;

    public OrgService(TGenericRepository<Org, UUID> repository, OrgRepository orgRepository) {
        super(repository);
        this.repository = orgRepository;
    }

    public Boolean checkOrgExistByCode (String code) {
        return repository.existsByCode(code);
    }

    public Boolean checkExistById(UUID id) {
        return repository.existsById(id);
    }

    public Org createOrg(Org org) {
        return this.repository.save(org);
    }

    public static OrgResponse getOrgResponse(Org source) {
        if(source == null) {
            return null;
        }
        OrgResponse resp = new OrgResponse();
        resp.setCode(source.getCode());
        resp.setName(source.getName());
        resp.setId(source.getId());
        return resp;
    }
}
