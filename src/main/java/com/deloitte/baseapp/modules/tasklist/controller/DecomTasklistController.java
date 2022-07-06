package com.deloitte.baseapp.modules.tasklist.controller;

import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.modules.tasklist.dto.DecomTasklistDetailsResponseDTO;
import com.deloitte.baseapp.modules.tasklist.dto.DecomTasklistResponseDTO;
import com.deloitte.baseapp.modules.tasklist.services.DecomTasklistService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api/decom-tasklist")
public class DecomTasklistController {

    @Autowired
    private DecomTasklistService decomTasklistService;

    @GetMapping(value = "/get")
    public MessageResponse<List<DecomTasklistResponseDTO>> getDecomTasklist() {

        try {

            log.info("Attempting to get Decom Task lists");
            List<DecomTasklistResponseDTO> decomTasklistResponseDTOS = decomTasklistService.mapToDecomTasklistDTO();

            return new MessageResponse<>(decomTasklistResponseDTOS);

        } catch (Exception e) {

            log.error("An  Error occurred at DecomTasklistController while attempting to GET Decom Task lists", e);

            return MessageResponse.ErrorWithCode(e.getMessage(), 400);

        }
    }

    @GetMapping(value = "/get/{id}")
    public MessageResponse<DecomTasklistResponseDTO> getDecomTasklistById(@PathVariable Long id) {

        try {

            log.info("Attempting to get Decom Task lists");
            DecomTasklistResponseDTO decomTasklistResponseDTOLst = decomTasklistService.mapToDecomTasklistDTOById(id);

            return new MessageResponse<>(decomTasklistResponseDTOLst);

        } catch (ObjectNotFoundException objNotFoundEx) {

            log.error("TAppSite with id: {} does not exist, failed with error: {} ",
                    id, objNotFoundEx.getMessage(), objNotFoundEx);
            return MessageResponse.ErrorWithCode(objNotFoundEx.getMessage(), 400);

        } catch (Exception e) {

            log.error("An  Error occurred at DecomTasklistController while attempting to GET Decom Task lists by Id", e);

            return MessageResponse.ErrorWithCode(e.getMessage(), 400);

        }
    }

    @GetMapping(value = "/get-details/{id}")
    public MessageResponse<List<DecomTasklistDetailsResponseDTO>> getDecomTasklistDetails(@PathVariable Long id) {
        try {
            log.info("Attempting to get Decom Tasklist details");
            List<DecomTasklistDetailsResponseDTO> decomTasklistDetails =
                    decomTasklistService.mapToDecomTasklistDetailsDTOById(id);
            return new MessageResponse<>(decomTasklistDetails);

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


}
