package com.deloitte.baseapp.modules.assignment.services;

import com.deloitte.baseapp.modules.assignment.repositories.TMtWarehouseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.deloitte.baseapp.modules.assignment.entities.TMtWarehouse;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


@Slf4j
@Service
public class WarehouseService {

    @Autowired
    private TMtWarehouseRepository tMtWarehouseRepository;

    public List<TMtWarehouse> getTMtWarehouses() {
        return tMtWarehouseRepository.findAll();
    }

    public Page<TMtWarehouse> getAllWarehouses(Pageable pageable, String postcode){
        log.info("TasklistService: Attempting to retrieve TAppSite for searching records from database");
        // exception if no param provided
        if (!postcode.equals("empty")) {
            return tMtWarehouseRepository.findAll(pageable, postcode);
        }
        else {
            return tMtWarehouseRepository.findAll(pageable);
        }
    }
}
