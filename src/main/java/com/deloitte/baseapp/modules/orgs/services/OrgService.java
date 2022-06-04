package com.deloitte.baseapp.modules.orgs.services;

import com.deloitte.baseapp.commons.TGenericRepository;
import com.deloitte.baseapp.commons.TGenericService;
import com.deloitte.baseapp.modules.orgs.entites.Org;
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

    public Org createOrg(Org org) {
        return this.repository.save(org);
    }
}
