package com.deloitte.baseapp.modules.orgs.repositories;

import com.deloitte.baseapp.commons.TGenericRepository;
import com.deloitte.baseapp.modules.orgs.entites.Org;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrgRepository extends TGenericRepository<Org, UUID> {
    public Org findOrgByCode(String code);
    public Boolean existsByCode(String code);
}
