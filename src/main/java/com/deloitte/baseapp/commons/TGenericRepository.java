package com.deloitte.baseapp.commons;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TGenericRepository<T extends TGenericEntity<T,ID>, ID>  extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
}
