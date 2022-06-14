package com.deloitte.baseapp.modules.teams.services;

import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.commons.tModules.TGenericService;
import com.deloitte.baseapp.modules.tAccount.repositories.TOrgUserRepository;
import com.deloitte.baseapp.modules.teams.entities.OrgUsrTeam;
import com.deloitte.baseapp.modules.teams.payloads.response.OrgUsrTeamResponse;
import com.deloitte.baseapp.modules.teams.repositories.OrgTeamRepository;
import com.deloitte.baseapp.modules.teams.repositories.OrgUsrTeamRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrgUsrTeamService extends TGenericService<OrgUsrTeam, UUID> {

    OrgUsrTeamRepository repository;
    OrgTeamRepository orgTeamRepository;
    TOrgUserRepository orgUserRepository;

    public OrgUsrTeamService(TGenericRepository<OrgUsrTeam, UUID> repository, OrgUsrTeamRepository orgUsrTeamRepository,
                             OrgTeamRepository orgTeamRepository,
                             TOrgUserRepository orgUserRepository) {
        super(repository);
        this.repository = orgUsrTeamRepository;
        this.orgTeamRepository = orgTeamRepository;
        this.orgUserRepository = orgUserRepository;
    }

    public OrgUsrTeam createUsrTeam (UUID userId, UUID orgTeamId) {
        OrgUsrTeam newInstance = new OrgUsrTeam();
        newInstance.setOrgTeam(orgTeamRepository.getById(orgTeamId));
        newInstance.setOrgUser(orgUserRepository.getById(userId));
        return repository.save(newInstance);
    }

    public static OrgUsrTeamResponse getOrgUsrTeam(OrgUsrTeam source) {
        OrgUsrTeamResponse newInstance = new OrgUsrTeamResponse();
        newInstance.setId(source.getId());
        newInstance.setOrgTeamId(source.getOrgTeam().getId());
        newInstance.setOrgTeamName(source.getOrgTeam().getName());
        newInstance.setUserName(source.getOrgUser().getName());
        newInstance.setUserId(source.getOrgUser().getId());
        return newInstance;
    }
}
