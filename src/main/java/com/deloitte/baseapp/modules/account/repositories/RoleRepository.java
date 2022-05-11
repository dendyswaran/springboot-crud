package com.deloitte.baseapp.modules.account.repositories;

import com.deloitte.baseapp.commons.GenericRepository;
import com.deloitte.baseapp.modules.account.entities.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends GenericRepository<Role> {
    Optional<Role> findByName(String name);
}
