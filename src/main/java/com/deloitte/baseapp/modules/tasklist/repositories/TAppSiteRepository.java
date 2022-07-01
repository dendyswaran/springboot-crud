package com.deloitte.baseapp.modules.tasklist.repositories;

import com.deloitte.baseapp.commons.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import com.deloitte.baseapp.modules.tasklist.entities.TAppSite;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface TAppSiteRepository extends GenericRepository<TAppSite> {
    @Query("from TAppSite s where lower(s.cd) LIKE concat('%', lower(:siteId), '%')")
    Page<TAppSite> findAll(Pageable pageable, @RequestParam String siteId);
}
