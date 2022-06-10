package com.deloitte.baseapp.modules.tAccount.repositories;

import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.modules.tAccount.entities.OrgUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TOrgUserRepository extends TGenericRepository<OrgUser, UUID> {

    public Optional<OrgUser> findByName(String username);
    public boolean existsByEmail(String email);
}
