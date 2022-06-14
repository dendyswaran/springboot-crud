package com.deloitte.baseapp.modules.tAccount.services;

import com.deloitte.baseapp.commons.ObjectNotFoundException;
import com.deloitte.baseapp.commons.tModules.TGenericRepository;
import com.deloitte.baseapp.commons.tModules.TGenericService;
import com.deloitte.baseapp.configs.security.jwt.GenericJwtResponse;
import com.deloitte.baseapp.configs.security.jwt.JwtUtils;
import com.deloitte.baseapp.modules.MTStatus.services.MtStatusService;
import com.deloitte.baseapp.modules.account.exceptions.RoleNotFoundException;
import com.deloitte.baseapp.modules.authentication.exception.BadCredentialException;
import com.deloitte.baseapp.modules.authentication.exception.EmailHasBeenUsedException;
import com.deloitte.baseapp.modules.authentication.payloads.SignInOrgUserRequest;
import com.deloitte.baseapp.modules.orgs.entites.OrgUsrUsrGroup;
import com.deloitte.baseapp.modules.orgs.repositories.OrgUsrGroupRepository;
import com.deloitte.baseapp.modules.orgs.repositories.OrgUsrUsrGroupRepository;
import com.deloitte.baseapp.modules.orgs.services.OrgService;
import com.deloitte.baseapp.modules.tAccount.entities.OrgUser;
import com.deloitte.baseapp.modules.tAccount.repositories.TOrgUserRepository;
import com.deloitte.baseapp.modules.tAuthentication.payloads.request.SignUpOrgUserRequest;
import com.deloitte.baseapp.modules.teams.entities.OrgUsrTeam;
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
    private final OrgUsrGroupRepository orgUsrGroupRepository;
    private final PasswordEncoder encoder;
    private final OrgUsrUsrGroupRepository orgUsrUsrGroupRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;
    private final MtStatusService mtStatusService;
    private final OrgUsrTeamService orgUsrTeamService;
    private final OrgService orgService;


    public OrgUserService(TGenericRepository<OrgUser, UUID> genericRepository,
                          TOrgUserRepository repository,
                          PasswordEncoder encoder,
                          JwtUtils jwtUtils,
                          AuthenticationManager authManager,
                          OrgUsrGroupRepository orgUsrGroupRepository,
                          OrgUsrUsrGroupRepository orgUsrUsrGroupRepository,
                          MtStatusService mtStatusService,
                          OrgUsrTeamService orgUsrTeamService,
                          OrgService orgService) {
        super(genericRepository);
        this.repository = repository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.authManager = authManager;
        this.orgUsrGroupRepository = orgUsrGroupRepository;
        this.orgUsrUsrGroupRepository = orgUsrUsrGroupRepository;
        this.mtStatusService = mtStatusService;
        this.orgUsrTeamService  = orgUsrTeamService;
        this.orgService = orgService;
    }

    public boolean checkExistByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public OrgUser createUser(OrgUser user) {
        return this.repository.save(user);
    }

    public OrgUser tSignup(final SignUpOrgUserRequest payload) throws EmailHasBeenUsedException, RoleNotFoundException {
        final boolean exists = this.checkExistByEmail(payload.getEmail());

        if (exists)
            throw new EmailHasBeenUsedException();


        OrgUser user = new OrgUser();
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

        try {
            user.setOrg(payload.getOrgId() != null ?
                    (orgService.checkExistById(UUID.fromString(payload.getOrgId())) ? orgService.get(UUID.fromString(payload.getOrgId())) : user.getOrg())
                    : user.getOrg());


        if(payload.getOrgUserGroupId() != null && userTemp != null) {
            String orgUserGroupId = payload.getOrgUserGroupId();
            if(orgUsrGroupRepository.existsById(UUID.fromString(orgUserGroupId))) {
                OrgUsrUsrGroup orgUsrUsrGroup = new OrgUsrUsrGroup();
                orgUsrUsrGroup.setOrgUsrGroup(orgUsrGroupRepository.getById(UUID.fromString(orgUserGroupId)));
                orgUsrUsrGroup.setOrgUser(userTemp);
                orgUsrUsrGroups.add(orgUsrUsrGroupRepository.save(orgUsrUsrGroup));
                user.setOrgUsrUsrGroups(orgUsrUsrGroups);
            }
        }

        if(payload.getOrgTeamId() != null) {
            Set<OrgUsrTeam> orgUsrTeams = user.getOrgUsrTeams()!=null ? user.getOrgUsrTeams() : new HashSet<>();
            if(orgUsrTeams.stream().noneMatch(orgUsrTeam -> orgUsrTeam.getOrgTeam().getId().equals(UUID.fromString(payload.getOrgTeamId())))) {
                OrgUsrTeam orgUsrTeam = orgUsrTeamService.createUsrTeam(user.getId(), UUID.fromString(payload.getOrgTeamId()));
                orgUsrTeams.add(orgUsrTeam);
                user.setOrgUsrTeams(orgUsrTeams);
            }
        }
        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }

        return user;
    }


    public GenericJwtResponse<UUID, UUID> signIn (final SignInOrgUserRequest payload) throws BadCredentialException {
        final Optional<OrgUser> optionalUser = repository.findByName(payload.getUsername());
        if(optionalUser.isEmpty())
            throw new BadCredentialException();

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String accessToken =  jwtUtils.generateJwtToken(authentication);
        final OrgUser user = optionalUser.get();
        Set<UUID> orgUsrGroupIds = user.getOrgUsrUsrGroups().stream()
                .map(orgUsrUsrGroup -> orgUsrUsrGroup.getOrgUsrGroup().getId())
                .collect(Collectors.toSet());
        return new GenericJwtResponse<>(accessToken,
                user.getId(),
                user.getName(),
                user.getEmail(),
                orgUsrGroupIds);
    }

    //TODO: Response need to be updated so that instead of return a null list, no parameter will be return
    // This can be achieve by using response payload, so that a  list of certain attribute would be returned
    public List<OrgUser> getAllUsers () {
        return repository.findAll();
    }

    public OrgUser getUser(UUID id) throws ObjectNotFoundException {
        return this.get(id);
    }

}
