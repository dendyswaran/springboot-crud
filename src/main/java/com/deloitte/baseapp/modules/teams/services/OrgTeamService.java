package com.deloitte.baseapp.modules.teams.services;

import com.deloitte.baseapp.commons.ObjectNotFoundException;
import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.commons.tModules.TGenericService;
import com.deloitte.baseapp.modules.orgs.entites.Org;
import com.deloitte.baseapp.modules.orgs.repositories.OrgRepository;
import com.deloitte.baseapp.modules.orgs.services.OrgService;
import com.deloitte.baseapp.modules.teams.entities.OrgTeam;
import com.deloitte.baseapp.modules.teams.payloads.request.OrgTeamCreateRequest;
import com.deloitte.baseapp.modules.teams.payloads.request.OrgTeamUpdateRequest;
import com.deloitte.baseapp.modules.teams.repositories.OrgTeamRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrgTeamService extends TGenericService<OrgTeam, UUID> {

    OrgTeamRepository repository;
    OrgRepository orgRepository;

    public OrgTeamService(TGenericRepository<OrgTeam, UUID> repository,
                          OrgTeamRepository orgTeamRepository,
                          OrgRepository orgRepository) {
        super(repository);
        this.repository = orgTeamRepository;
        this.orgRepository = orgRepository;
    }

    public OrgTeam createOrgTeam(OrgTeamCreateRequest payload) {
        OrgTeam orgTeam = new OrgTeam();
        orgTeam.setName(payload.getName());
        orgTeam.setCode(payload.getCode());
        Org org = orgRepository.findById(UUID.fromString(payload.getOrgId())).orElseThrow(
                () ->  new IllegalArgumentException("Org cannot be found")
        );
        orgTeam.setOrg(org);
        return repository.save(orgTeam);
    }

    public OrgTeam updateOrgTeam(UUID id, OrgTeamUpdateRequest payload) throws ObjectNotFoundException {
        OrgTeam orgTeam = repository.getById(id);
        orgTeam.setCode(payload.getCode() == null ? payload.getCode() : orgTeam.getCode());
        orgTeam.setName(payload.getName() != null ? payload.getName() : orgTeam.getName());
        orgTeam.setOrg(payload.getOrgId() != null ?
                (orgRepository.existsById(UUID.fromString(payload.getOrgId())) ?
                        orgRepository.getById(UUID.fromString(payload.getOrgId())) :
                        orgTeam.getOrg()) :
                orgTeam.getOrg());
        return this.update(id, orgTeam);
    }
}
