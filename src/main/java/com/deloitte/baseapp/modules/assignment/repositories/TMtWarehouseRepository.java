package com.deloitte.baseapp.modules.assignment.repositories;

import com.deloitte.baseapp.commons.GenericRepository;
import com.deloitte.baseapp.modules.assignment.entities.TMtWarehouse;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface TMtWarehouseRepository extends GenericRepository<TMtWarehouse> {

    @Query("from TMtWarehouse w where lower(w.postcode) LIKE concat('%', lower(:postcode), '%')")
    Page<TMtWarehouse> findAll(Pageable pageable, @RequestParam String postcode);
}
