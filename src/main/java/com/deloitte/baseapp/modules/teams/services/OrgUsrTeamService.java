package com.deloitte.baseapp.modules.teams.services;

import com.deloitte.baseapp.commons.ObjectNotFoundException;
import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.commons.tModules.TGenericService;
import com.deloitte.baseapp.modules.tAccount.entities.OrgUser;
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

    public OrgUsrTeam createUsrTeam (OrgUser user, UUID orgTeamId) throws ObjectNotFoundException {
        OrgUsrTeam newInstance = new OrgUsrTeam();
        newInstance.setOrgTeam(orgTeamRepository.findById(orgTeamId).orElseThrow(ObjectNotFoundException::new));
        newInstance.setOrgUser(user);
        return repository.save(newInstance);
    }


}
