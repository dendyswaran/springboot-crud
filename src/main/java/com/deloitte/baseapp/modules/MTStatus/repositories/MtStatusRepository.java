package com.deloitte.baseapp.modules.MTStatus.repositories;

import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.modules.MTStatus.entities.MtStatus;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MtStatusRepository extends TGenericRepository<MtStatus, UUID> {
    boolean existsById(UUID id);
    MtStatus getMtStatusByCode(String id);
}
