package com.deloitte.baseapp.modules.account.services;

import com.deloitte.baseapp.commons.TGenericRepository;
import com.deloitte.baseapp.commons.TGenericService;
import com.deloitte.baseapp.modules.account.entities.OrgUser;
import com.deloitte.baseapp.modules.account.repositories.TOrgUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class TOrgUserService extends TGenericService<OrgUser, UUID> {

    private final TOrgUserRepository repository;

    public TOrgUserService(TGenericRepository<OrgUser, UUID> genericRepository, TOrgUserRepository repository) {
        super(genericRepository);
        this.repository = repository;
    }

    public boolean checkExistByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public OrgUser createUser(OrgUser user) {
        return this.repository.save(user);
    }

}
