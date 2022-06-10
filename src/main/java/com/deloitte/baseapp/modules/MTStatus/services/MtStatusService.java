package com.deloitte.baseapp.modules.MTStatus.services;

import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.commons.tModules.TGenericService;
import com.deloitte.baseapp.modules.MTStatus.entities.MtStatus;
import com.deloitte.baseapp.modules.MTStatus.repositories.MtStatusRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MtStatusService extends TGenericService<MtStatus, UUID> {
    private final MtStatusRepository repository;
    public MtStatusService(TGenericRepository<MtStatus, UUID> repository, MtStatusRepository mtStatusRepository) {
        super(repository);
        this.repository = mtStatusRepository;
    }

    public Boolean existById(UUID id) {
        return repository.existsById(id);
    }

    public MtStatus getByCode(String code) {
        return repository.getMtStatusByCode(code);
    }
    
}
