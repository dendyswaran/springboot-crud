package com.deloitte.baseapp.modules.tasklist.controller;

import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.modules.tasklist.entities.TAppSite;
import com.deloitte.baseapp.modules.tasklist.entities.TAppSiteEqp;
import com.deloitte.baseapp.modules.tasklist.services.TasklistService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/tasklist")
public class TasklistController {

    @Autowired
    private TasklistService tasklistService;


    @GetMapping("/get")
    public MessageResponse<List<TAppSite>> getAllTAppSites() {

        try {
            List<TAppSite> tAppSiteList = tasklistService.getTAppSites();
            return new MessageResponse<>(tAppSiteList);

        } catch (Exception ex) {

            return MessageResponse.ErrorWithCode("TAppSites Retrieval Unsuccessful", 400);

        }
    }

    @GetMapping("/get/{id}")
    public MessageResponse<TAppSite> getTAppSitesByID(@PathVariable Long id) {

        try {

            log.info("Attempting to get TAppSite for id: {}", id);
            TAppSite tAppSite = tasklistService.getTAppSiteById(id);
            return new MessageResponse<>(tAppSite);

        } catch (ObjectNotFoundException objNotFoundEx) {

            log.error("TAppSite with id: {} does not exist, failed with error: {} ",
                    id, objNotFoundEx.getMessage(), objNotFoundEx);
            return MessageResponse.ErrorWithCode(objNotFoundEx.getMessage(), 400);

        } catch (Exception e) {

            log.error("An  Error occurred at TasklistController while attempting to GET TAppSite by Id", e);
            return MessageResponse.ErrorWithCode(e.getMessage(), 400);

        }

    }

    @GetMapping("/get-details/{id}")
    public MessageResponse<List<TAppSiteEqp>> getTasklistDetailsByID(@PathVariable Long id) {
        try {

            List<TAppSiteEqp> tAppSiteEqps =
                    tasklistService.getTAppSiteEqpsById(id);
            return new MessageResponse<>(tAppSiteEqps);

        } catch (ObjectNotFoundException objNotFoundEx) {

            log.error("TAppSite with id: {} does not exist, failed with error: {} ",
                    id, objNotFoundEx.getMessage(), objNotFoundEx);
            return MessageResponse.ErrorWithCode(objNotFoundEx.getMessage(), 400);

        } catch (IllegalStateException illegalStateEx) {

            log.error("TAppSite with id: {} exists, but TAppSiteEqpList contains no equipment, failed with error: {} ",
                    id, illegalStateEx.getMessage(), illegalStateEx);
            return MessageResponse.ErrorWithCode(illegalStateEx.getMessage(), 400);

        } catch (Exception e) {

            log.error("Failed to retrieve TAppSiteEqps for id: {}, failed with error: {}",
                    id, e.getMessage(), e);
            return MessageResponse.ErrorWithCode(e.getMessage(), 400);

        }
    }


    @PostMapping("/insert-dummy")
    public void insertDummyDataToTAppSite() {
        log.info("Insert Dummy Data");

        tasklistService.InsertDummyIntoTAppSite();

        log.info("Insert successful");
    }

}