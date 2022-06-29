package com.deloitte.baseapp.modules.MTStatus.controllers;

import com.deloitte.baseapp.commons.GenericRequestPayload;
import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.commons.tModules.TGenericController;
import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.modules.MTStatus.entities.MtStatus;
import com.deloitte.baseapp.modules.MTStatus.payloads.MtStatusCreateRequest;
import com.deloitte.baseapp.modules.MTStatus.services.MtStatusService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/mt-status")
public class MtStatusControllers extends TGenericController<MtStatus, UUID> {
    public MtStatusService service;
    public MtStatusControllers(TGenericRepository<MtStatus, UUID> repository,
                               MtStatusService service) {
        super(repository, "mtStatusService");
        this.service = service;
    }

    @PostMapping("/create")
    public MessageResponse<?> MtStatusCreate(@RequestBody MtStatusCreateRequest payload) {
        try {
            MtStatus mtStatus = new MtStatus();
            mtStatus.setCode(payload.getCode());
            mtStatus.setDscp(payload.getDscp());
            return new MessageResponse<>(service.create(mtStatus));
        } catch (Exception e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), 501);
        }
    }
}
