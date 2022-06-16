package com.deloitte.baseapp.modules.account.repositories;

import com.deloitte.baseapp.modules.account.entities.UserTokenRedis;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRedisRepository extends CrudRepository<UserTokenRedis, Long> {
}
