package com.deloitte.baseapp.modules.orgs.repositories;

import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.modules.orgs.entites.Org;
import com.deloitte.baseapp.modules.orgs.entites.OrgUsrGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface OrgUsrGroupRepository extends TGenericRepository<OrgUsrGroup, UUID> {
    public OrgUsrGroup findOrgUsrGroupById(UUID id);

    public boolean existsById(UUID id);
    public List<OrgUsrGroup> getOrgUsrGroupsByOrg(Org org);
}
