package com.deloitte.baseapp.modules.assignment.controllers;

import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.modules.assignment.entities.TMtWarehouse;
import com.deloitte.baseapp.modules.assignment.services.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/warehouse-assignment")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return new ResponseEntity<>("hellooo yeahhh", HttpStatus.OK);
    }

    @GetMapping("/get")
    public MessageResponse<List<TMtWarehouse>> getAllTMtWarehouses() {
        try {
            List<TMtWarehouse> tMtWarehouseList = warehouseService.getTMtWarehouses();
            return new MessageResponse<>(tMtWarehouseList);
        } catch (Exception ex) {
            return MessageResponse.ErrorWithCode("TMtWarehouses Retrieval Unsuccessful", 400);
        }
    }

    // for paging and searching
    @GetMapping("/list-warehouse")
    public MessageResponse<Page<TMtWarehouse>> getAllWarehouses(Pageable pageable, @RequestParam(defaultValue = "empty") String postcode) {
        try {
            Page<TMtWarehouse> tMtWarehousePage = warehouseService.getAllWarehouses(pageable, postcode);
            return new MessageResponse<>(tMtWarehousePage);
        } catch (Exception ex) {
            return MessageResponse.ErrorWithCode("TAppSites Search Retrieval Unsuccessful", 400);
        }
    }

    // Data insert manually in PGAdmin
}
