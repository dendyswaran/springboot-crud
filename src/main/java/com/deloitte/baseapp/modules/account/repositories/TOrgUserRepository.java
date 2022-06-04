package com.deloitte.baseapp.modules.account.repositories;

import com.deloitte.baseapp.commons.TGenericRepository;
import com.deloitte.baseapp.modules.account.entities.OrgUser;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TOrgUserRepository extends TGenericRepository<OrgUser, UUID> {

    public boolean existsByEmail(String email);
}
