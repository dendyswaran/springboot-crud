package com.deloitte.baseapp.modules.orgs.repositories;

import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.modules.orgs.entites.OrgUsrGroup;
import com.deloitte.baseapp.modules.orgs.entites.OrgUsrUsrGroup;
import com.deloitte.baseapp.modules.tAccount.entities.OrgUser;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrgUsrUsrGroupRepository extends TGenericRepository<OrgUsrUsrGroup, UUID> {
    public OrgUsrUsrGroup findOrgUsrUsrGroupByOrgUser(OrgUser user);

    public OrgUsrUsrGroup findOrgUsrUsrGroupByOrgUsrGroup(OrgUsrGroup orgUsrGroup);
}
