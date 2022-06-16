package com.deloitte.baseapp.modules.account.repositories;

import com.deloitte.baseapp.commons.GenericRepository;
import com.deloitte.baseapp.modules.account.entities.UserToken;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends GenericRepository<UserToken> {
}
