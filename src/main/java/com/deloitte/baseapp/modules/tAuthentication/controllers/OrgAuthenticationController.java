package com.deloitte.baseapp.modules.tAuthentication.controllers;

import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.commons.ObjectNotFoundException;
import com.deloitte.baseapp.configs.security.jwt.GenericJwtResponse;
import com.deloitte.baseapp.modules.account.exceptions.RoleNotFoundException;
import com.deloitte.baseapp.modules.authentication.exception.BadCredentialException;
import com.deloitte.baseapp.modules.authentication.exception.EmailHasBeenUsedException;
import com.deloitte.baseapp.modules.authentication.payloads.SignInOrgUserRequest;
import com.deloitte.baseapp.modules.tAccount.entities.OrgUser;
import com.deloitte.baseapp.modules.tAccount.services.OrgUserService;
import com.deloitte.baseapp.modules.tAuthentication.payloads.request.SignUpOrgUserRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/org-auth")
public class OrgAuthenticationController {
    OrgUserService service;

    public OrgAuthenticationController(OrgUserService service) {
        this.service = service;
    }

    //register
    @PostMapping("/signup")
    public MessageResponse<?> signUpOrgUser(@Valid @RequestBody SignUpOrgUserRequest payload) {
        try {
            OrgUser user = service.tSignup(payload);
            if(user == null) {
                return MessageResponse.ErrorWithCode("Server error", 500);
            }
            return new MessageResponse<>("New User Successfully Registered");
        } catch (RoleNotFoundException | RuntimeException | EmailHasBeenUsedException e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), 500);
        } catch (ObjectNotFoundException e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), e.getCode());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //signIn
    @PostMapping("signIn")
    public MessageResponse<?> signInOrgUser(@Valid @RequestBody SignInOrgUserRequest payload) {
        try {
            final GenericJwtResponse<UUID, UUID> jwt = service.signIn(payload);
            return new MessageResponse<>(jwt);
        } catch (BadCredentialException e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), 400);
        }
    }


}
