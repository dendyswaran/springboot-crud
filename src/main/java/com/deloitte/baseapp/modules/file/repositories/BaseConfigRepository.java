package com.deloitte.baseapp.modules.file.repositories;

import com.deloitte.baseapp.commons.GenericRepository;
import com.deloitte.baseapp.modules.account.entities.Role;
import com.deloitte.baseapp.modules.account.entities.User;
import com.deloitte.baseapp.modules.account.entities.UserToken;
import com.deloitte.baseapp.modules.file.entitites.BaseConfig;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseConfigRepository extends JpaRepository<BaseConfig, String> {
}
