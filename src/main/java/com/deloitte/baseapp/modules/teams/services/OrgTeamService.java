package com.deloitte.baseapp.modules.teams.services;

import com.deloitte.baseapp.commons.ObjectNotFoundException;
import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.commons.tModules.TGenericService;
import com.deloitte.baseapp.modules.orgs.entites.Org;
import com.deloitte.baseapp.modules.orgs.payloads.response.OrgResponse;
import com.deloitte.baseapp.modules.orgs.repositories.OrgRepository;
import com.deloitte.baseapp.modules.orgs.services.OrgService;
import com.deloitte.baseapp.modules.teams.entities.OrgTeam;
import com.deloitte.baseapp.modules.teams.payloads.request.OrgTeamCreateRequest;
import com.deloitte.baseapp.modules.teams.payloads.request.OrgTeamUpdateRequest;
import com.deloitte.baseapp.modules.teams.payloads.response.OrgTeamResponse;
import com.deloitte.baseapp.modules.teams.repositories.OrgTeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<OrgTeamResponse> getOrgList() {
        return this.getAll().stream()
                .map(OrgTeamService::getOrgTeamResponse)
                .collect(Collectors.toList());
    }

    public static OrgTeamResponse getOrgTeamResponse(OrgTeam source) {
        if(source == null) {
            return null;
        }
        OrgTeamResponse resp = new OrgTeamResponse();
        resp.setId(source.getId());
        resp.setOrgId(source.getOrg().getId());
        resp.setCode(source.getCode());
        resp.setName(source.getName());
        resp.setOrgName(source.getOrg().getName());
        resp.setOrgCode(source.getOrg().getCode());
        return resp;
    }
}
