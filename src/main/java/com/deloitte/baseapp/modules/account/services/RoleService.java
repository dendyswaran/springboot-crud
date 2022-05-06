package com.deloitte.baseapp.modules.account.services;

import com.deloitte.baseapp.commons.GenericRepository;
import com.deloitte.baseapp.commons.GenericService;
import com.deloitte.baseapp.modules.account.entities.Role;
import com.deloitte.baseapp.modules.account.exceptions.RoleNotFoundException;
import com.deloitte.baseapp.modules.account.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class RoleService extends GenericService<Role> {

    @Autowired
    private RoleRepository roleRepository;

    public RoleService(GenericRepository<Role> repository) {
        super(repository);
    }

    public Role getRoleByName(final String roleName) throws RoleNotFoundException {
        Optional<Role> optionalRole = roleRepository.findByName(roleName);
        if (optionalRole.isEmpty())
            throw new RoleNotFoundException(roleName);

        return optionalRole.get();
    }

}
