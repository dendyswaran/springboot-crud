package com.deloitte.baseapp.modules.account.repositories;

import java.util.Optional;

import com.deloitte.baseapp.modules.account.entities.ERole;
import com.deloitte.baseapp.modules.account.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
