package com.deloitte.baseapp.modules.teams.repositories;

import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.modules.teams.entities.OrgTeam;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrgTeamRepository extends TGenericRepository<OrgTeam, UUID> {

}
