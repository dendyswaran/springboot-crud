package com.deloitte.baseapp.modules.tAccount.services;

import com.deloitte.baseapp.commons.ObjectNotFoundException;
import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.commons.tModules.TGenericService;
import com.deloitte.baseapp.configs.security.jwt.GenericJwtResponse;
import com.deloitte.baseapp.configs.security.jwt.JwtUtils;
import com.deloitte.baseapp.modules.MTStatus.services.MtStatusService;
import com.deloitte.baseapp.modules.authentication.exception.BadCredentialException;
import com.deloitte.baseapp.modules.authentication.exception.EmailHasBeenUsedException;
import com.deloitte.baseapp.modules.authentication.exception.UsernameHasBeenUsedException;
import com.deloitte.baseapp.modules.authentication.payloads.SignInOrgUserRequest;
import com.deloitte.baseapp.modules.orgs.entites.OrgUsrGroup;
import com.deloitte.baseapp.modules.orgs.entites.OrgUsrUsrGroup;
import com.deloitte.baseapp.modules.orgs.services.OrgService;
import com.deloitte.baseapp.modules.orgs.services.OrgUsrGroupService;
import com.deloitte.baseapp.modules.orgs.services.OrgUsrUsrGroupService;
import com.deloitte.baseapp.modules.tAccount.entities.OrgUser;
import com.deloitte.baseapp.modules.tAccount.repositories.TOrgUserRepository;
import com.deloitte.baseapp.modules.tAuthentication.payloads.request.SignUpOrgUserRequest;
import com.deloitte.baseapp.modules.teams.entities.OrgTeam;
import com.deloitte.baseapp.modules.teams.entities.OrgUsrTeam;
import com.deloitte.baseapp.modules.teams.services.OrgTeamService;
import com.deloitte.baseapp.modules.teams.services.OrgUsrTeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrgUserService extends TGenericService<OrgUser, UUID> {

    private final TOrgUserRepository repository;
    private final OrgUsrGroupService orgUsrGroupService;
    private final PasswordEncoder encoder;
    private final OrgUsrUsrGroupService orgUsrUsrGroupService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;
    private final MtStatusService mtStatusService;
    private final OrgUsrTeamService orgUsrTeamService;
    private final OrgService orgService;
    private final OrgTeamService orgTeamService;


    public OrgUserService(TGenericRepository<OrgUser, UUID> genericRepository,
                          TOrgUserRepository repository,
                          PasswordEncoder encoder,
                          JwtUtils jwtUtils,
                          AuthenticationManager authManager,
                          OrgUsrGroupService orgUsrGroupService,
                          OrgUsrUsrGroupService orgUsrUsrGroupService,
                          MtStatusService mtStatusService,
                          OrgUsrTeamService orgUsrTeamService,
                          OrgService orgService,
                          OrgTeamService orgTeamService) {
        super(genericRepository);
        this.repository = repository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.authManager = authManager;
        this.orgUsrGroupService = orgUsrGroupService;
        this.orgUsrUsrGroupService = orgUsrUsrGroupService;
        this.mtStatusService = mtStatusService;
        this.orgUsrTeamService  = orgUsrTeamService;
        this.orgService = orgService;
        this.orgTeamService = orgTeamService;
    }

    public boolean checkExistByEmail(String email) {
        return repository.existsByEmail(email);
    }
    public boolean checkExistByName(String name) {return repository.existsByName(name); }

    public OrgUser createUser(OrgUser user) {
        return this.repository.save(user);
    }

    public OrgUser tSignup(final SignUpOrgUserRequest payload) throws Exception {
        final boolean existsByEmail = this.checkExistByEmail(payload.getEmail());

        if (existsByEmail)
            throw new EmailHasBeenUsedException();

        if(checkExistByName(payload.getName())) {
            throw new UsernameHasBeenUsedException();
        }
        OrgUser user = new OrgUser();
        user.setCode(payload.getCode());
        user.setName(payload.getName());
        user.setEmail(payload.getEmail());
        user.setPassword(encoder.encode(payload.getPassword()));

        // set  MTStatus, may have problem when first load
        //  make sure that the data in database exist
        user.setMtStatus(mtStatusService.getByCode("01"));

        // check roles
        Set<OrgUsrUsrGroup> orgUsrUsrGroups = new HashSet<>();

//        log.info("User has been saved: " + user);
        OrgUser userTemp = this.createUser(user);
            // set org for the users
            user.setOrg(payload.getOrgId() != null
                    ?(orgService.checkExistById(UUID.fromString(payload.getOrgId()))
                            ? orgService.get(UUID.fromString(payload.getOrgId()))
                            : user.getOrg())
                    : user.getOrg());



            if(payload.getOrgUsrGroupId() != null && userTemp != null) {
                String orgUserGroupId = payload.getOrgUsrGroupId();
                if(orgUsrGroupService.existsById(UUID.fromString(orgUserGroupId))) {
//                    OrgUsrUsrGroup orgUsrUsrGroup = new OrgUsrUsrGroup();
//                    orgUsrUsrGroup.setOrgUsrGroup(orgUsrGroupService.get(UUID.fromString(orgUserGroupId)));
//                    orgUsrUsrGroup.setOrgUser(userTemp);
//                    orgUsrUsrGroups.add(orgUsrUsrGroupService.create(orgUsrUsrGroup));
//                    user.setOrgUsrUsrGroups(orgUsrUsrGroups);
                    user = setUserToHasOneOrgUsrUsrGrpByUpdating(user, orgUserGroupId);
                }
            }

            if(payload.getOrgTeamId() != null && userTemp != null) {
                user = setUserToHasOneOrgTeamByUpdating(userTemp, payload.getOrgTeamId());
            }

        assert userTemp != null;
        return update(userTemp.getId(), user);
    }


    public GenericJwtResponse<UUID, UUID> signIn (final SignInOrgUserRequest payload) throws BadCredentialException, IllegalArgumentException {
        // find for the username
        // for additional options, can also check with email and some other attributes (unique attributes recommended)
        final Optional<OrgUser> optionalUser = repository.findByName(payload.getUsername());
        if(optionalUser.isEmpty())
            throw new BadCredentialException();
        final OrgUser user = optionalUser.get();
        if(user.getMtStatus().getCode().equals("02")) {
            throw new IllegalArgumentException("User do not have permission");
        }
        // for authentication purpose, can use other values such as username, password
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String accessToken =  jwtUtils.generateJwtToken(authentication);
        Set<UUID> orgUsrGroupIds = user.getOrgUsrUsrGroups().stream()
                .map(orgUsrUsrGroup -> orgUsrUsrGroup.getOrgUsrGroup().getId())
                .collect(Collectors.toSet());
        return new GenericJwtResponse<>(accessToken,
                user.getId(),
                user.getName(),
                user.getEmail(),
                orgUsrGroupIds);
    }

    public List<OrgUser> getAllUsers () {
        return repository.findAll();
    }

    public OrgUser getUser(UUID id) throws ObjectNotFoundException {
        return this.get(id);
    }


    public OrgUser setUserToHasMultipleOrgTeam (OrgUser user, String orgTeamId) throws ObjectNotFoundException {
        Set<OrgUsrTeam> orgUsrTeams = user.getOrgUsrTeams()!=null ? user.getOrgUsrTeams() : new HashSet<>();
        if(orgUsrTeams.stream().noneMatch(orgUsrTeam -> orgUsrTeam.getOrgTeam().getId().equals(UUID.fromString(orgTeamId)))) {
            OrgUsrTeam orgUsrTeam = orgUsrTeamService.createUsrTeam(user, UUID.fromString(orgTeamId));
            orgUsrTeams.add(orgUsrTeam);
        }
        user.setOrgUsrTeams(orgUsrTeams);
        return user;
    }

    public OrgUser setUserToHasOneOrgTeamByUpdating(OrgUser user, String orgTeamId) throws ObjectNotFoundException {
        Set<OrgUsrTeam> orgUsrTeams = user.getOrgUsrTeams() != null ? user.getOrgUsrTeams() : new HashSet<>();
        if(!orgUsrTeams.isEmpty()) {
            // update the row in the org_usr_team
            OrgTeam orgTeam = orgTeamService.get(UUID.fromString(orgTeamId));
            OrgUsrTeam orgUsrTeam = orgUsrTeams.iterator().next();
            orgUsrTeam.setOrgTeam(orgTeam);
            orgUsrTeamService.update(orgUsrTeam.getId(), orgUsrTeam);
            return get(user.getId());
        }else {
            orgUsrTeams.add(orgUsrTeamService.createUsrTeam(user, UUID.fromString(orgTeamId)));
            user.setOrgUsrTeams(orgUsrTeams);
        }
        return user;
    }

    public OrgUser setUserToHasOneOrgTeamByDeleteAndSave(OrgUser user, String orgTeamId) throws ObjectNotFoundException {
        Set<OrgUsrTeam> orgUsrTeams = user.getOrgUsrTeams() != null ? user.getOrgUsrTeams() : new HashSet<>();
        if(!orgUsrTeams.isEmpty()) {
            for (OrgUsrTeam orgUsrTeam : orgUsrTeams) {
                orgUsrTeamService.delete(orgUsrTeam.getId());
            }
            orgUsrTeams.clear();
        }
        orgUsrTeams.add(orgUsrTeamService.createUsrTeam(user, UUID.fromString(orgTeamId)));
        user.setOrgUsrTeams(orgUsrTeams);
        return user;
    }

    public OrgUser setUserToHasOneOrgUsrUsrGrpByUpdating(OrgUser user, String orgUsrGroupId) throws ObjectNotFoundException {
        Set<OrgUsrUsrGroup> orgUsrUsrGroups = user.getOrgUsrUsrGroups() != null ? user.getOrgUsrUsrGroups() : new HashSet<>();
        if(!orgUsrUsrGroups.isEmpty()) {
            // update row in t_org_usr_usr_grp
            OrgUsrGroup orgUsrGroup = orgUsrGroupService.get(UUID.fromString(orgUsrGroupId));
            OrgUsrUsrGroup orgUsrUsrGroup = orgUsrUsrGroups.iterator().next();
            orgUsrUsrGroup.setOrgUsrGroup(orgUsrGroup);
            orgUsrUsrGroupService.update(orgUsrUsrGroup.getId(), orgUsrUsrGroup);
            return get(user.getId());
        }else {
            orgUsrUsrGroups.add(orgUsrUsrGroupService.createOrgUsrUsrGroup(user, UUID.fromString(orgUsrGroupId)));
            user.setOrgUsrUsrGroups(orgUsrUsrGroups);
            return user;
        }

    }

    public OrgUser setUserToHasOneOrgUsrUsrGrpByDeleteAndSave(OrgUser user, String orgUsrGroupId) throws ObjectNotFoundException {
        Set<OrgUsrUsrGroup> orgUsrUsrGroups = user.getOrgUsrUsrGroups() != null  ? user.getOrgUsrUsrGroups() : new HashSet<>();
        if(!orgUsrUsrGroups.isEmpty()) {
            for(OrgUsrUsrGroup orgUsrUsrGroup : orgUsrUsrGroups) {
                orgUsrUsrGroupService.delete(orgUsrUsrGroup.getId());
            }
            orgUsrUsrGroups.clear();
        }
        orgUsrUsrGroups.add(orgUsrUsrGroupService.createOrgUsrUsrGroup(user,UUID.fromString(orgUsrGroupId)));
        user.setOrgUsrUsrGroups(orgUsrUsrGroups);
        return user;
    }

    public OrgUser setUserUserToHasMultipleOrgUsrUsrGroup(OrgUser user, String orgUsrGroupId) throws ObjectNotFoundException {
        Set<OrgUsrUsrGroup> orgUsrUsrGroups = user.getOrgUsrUsrGroups()!=null ? user.getOrgUsrUsrGroups() : new HashSet<>();
        if(orgUsrUsrGroups.stream().noneMatch(orgUsrUsrGroup -> orgUsrUsrGroup.getOrgUsrGroup().getId().equals(UUID.fromString(orgUsrGroupId)))) {
            OrgUsrUsrGroup orgUsrTeam = orgUsrUsrGroupService.createOrgUsrUsrGroup(user, UUID.fromString(orgUsrGroupId));
            orgUsrUsrGroups.add(orgUsrTeam);
        }
        user.setOrgUsrUsrGroups(orgUsrUsrGroups);
        return user;
    }
}