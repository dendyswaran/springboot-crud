package com.deloitte.baseapp.modules.tasklist.controller;

import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.modules.tasklist.dto.IohTasklistDetailsResponseDTO;
import com.deloitte.baseapp.modules.tasklist.dto.IohTasklistResponseDTO;
import com.deloitte.baseapp.modules.tasklist.services.IohTasklistService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/ioh-tasklist")
public class IohTasklistController {

    @Autowired
    private IohTasklistService iohTasklistService;

//    // TODO: to be removed
//    @Autowired
//    private GenericTasklistService<IohTasklistResponseDTO> genericTasklistService;

    @GetMapping(value = "/get-message-response")
    public MessageResponse<List<IohTasklistResponseDTO>> getTAppSitesMessageResponse() {

        try {

            log.info("Attempting to get IOH Task lists");
            List<IohTasklistResponseDTO> iohTasklistResponseDTOS = iohTasklistService.mapToIohTasklistDTO();

//            return new ResponseEntity<>(iohTasklistResponseDTOS, HttpStatus.OK);
            return new MessageResponse<>(iohTasklistResponseDTOS);

        } catch (Exception e) {

            log.error("An  Error occurred at IohTasklistController while attempting to GET IOH Task lists", e);

            return MessageResponse.ErrorWithCode(e.getMessage(), 400);

        }
    }

    @GetMapping(value = "/get")
    public ResponseEntity<List<IohTasklistResponseDTO>> getTAppSites() {

        try {

            log.info("Attempting to get IOH Task lists");
            List<IohTasklistResponseDTO> iohTasklistResponseDTOS = iohTasklistService.mapToIohTasklistDTO();

            return new ResponseEntity<>(iohTasklistResponseDTOS, HttpStatus.OK);

        } catch (Exception e) {

            log.error("An  Error occurred at IohTasklistController while attempting to GET IOH Task lists", e);

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<IohTasklistResponseDTO> getTAppSiteById(@PathVariable Long id) {

        try {

            log.info("Attempting to retrieve IohTasklist for id: {}", id);
            IohTasklistResponseDTO iohTasklistResponseDTO = iohTasklistService.mapToIohTasklistDTOById(id);

            return new ResponseEntity<>(iohTasklistResponseDTO, HttpStatus.OK);

        } catch (Exception e) {

            log.error("An Error occurred at IohTasklistController while attempting to GET the IOH Task list for {}", id, e);

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping(value = "/get-tasklist/{id}")
    public MessageResponse<List<IohTasklistDetailsResponseDTO>> getTAppSiteEqpsById(@PathVariable Long id) {
        try {
            log.info("Attempting to get Ioh Tasklist details");
            List<IohTasklistDetailsResponseDTO> iohTasklistDetails =
                    iohTasklistService.mapToIohTasklistDetailsDTOById(id);
            return new MessageResponse<>(iohTasklistDetails);

        } catch (ObjectNotFoundException objNotFoundEx) {

            log.error("TAppSite with id: {} does not exist, failed with error: {} ",
                    id, objNotFoundEx.getMessage(), objNotFoundEx);
            return MessageResponse.ErrorWithCode(objNotFoundEx.getMessage(), 400);

        } catch (IllegalStateException illegalStateEx) {

            log.error("TAppSite with id: {} exists, but TAppSiteEqpList contains no equipment, failed with error: {} ",
                    id, illegalStateEx.getMessage(), illegalStateEx);
            return MessageResponse.ErrorWithCode(illegalStateEx.getMessage(), 400);

        } catch (Exception e) {

            log.error("Failed to retrieve tasklist details for id: {}, failed with error: {}",
                    id, e.getMessage(), e);
            return MessageResponse.ErrorWithCode(e.getMessage(), 400);
        }
    }
//
//    // TODO: just a concept
//    @GetMapping(value = "/get-generic")
//    public ResponseEntity<List<IohTasklistResponseDTO>> getTAppSitesGeneric() {
//
//        try {
//
//            log.info("Attempting to get IOH Task lists");
//            List<IohTasklistResponseDTO> iohTasklistResponseDTOS = genericTasklistService.mapToIohTasklistDTO();
//
//            return new ResponseEntity<>(iohTasklistResponseDTOS, HttpStatus.OK);
//
//        } catch (Exception e) {
//
//            log.error("An  Error occurred at IohTasklistController while attempting to GET IOH Task lists", e);
//
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//
//        }
//    }


}
