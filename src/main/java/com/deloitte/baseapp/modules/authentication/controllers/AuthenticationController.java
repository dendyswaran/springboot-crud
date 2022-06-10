package com.deloitte.baseapp.modules.authentication.controllers;

import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.configs.security.jwt.GenericJwtResponse;
import com.deloitte.baseapp.modules.account.entities.Role;
import com.deloitte.baseapp.modules.account.entities.User;
import com.deloitte.baseapp.modules.account.exceptions.RoleNotFoundException;
import com.deloitte.baseapp.modules.authentication.exception.BadCredentialException;
import com.deloitte.baseapp.modules.authentication.exception.EmailHasBeenUsedException;
import com.deloitte.baseapp.modules.authentication.payloads.ForgotPasswordRequest;
import com.deloitte.baseapp.modules.authentication.payloads.SigninRequest;
import com.deloitte.baseapp.modules.authentication.payloads.SignupRequest;
import com.deloitte.baseapp.modules.authentication.services.AuthenticationService;
import com.deloitte.baseapp.modules.tAccount.services.OrgUserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final OrgUserService orgUserService;

    public AuthenticationController(AuthenticationService authenticationService, OrgUserService orgUserService) {
        this.authenticationService = authenticationService;
        this.orgUserService = orgUserService;
    }

    @PostMapping("/signup")
    public MessageResponse<?> signup(@Valid @RequestBody SignupRequest payload) {
        try {
            User user = authenticationService.signup(payload);
            return new MessageResponse<>(user);
        } catch (final EmailHasBeenUsedException | RoleNotFoundException ex) {
            return MessageResponse.ErrorWithCode(ex.getMessage(), 400);
        }
    }

    @PostMapping("/signin")
    public MessageResponse<?> signin(@Valid @RequestBody SigninRequest payload) {
        try {
            final GenericJwtResponse<Role, Long> jwt = authenticationService.signin(payload);
            return new MessageResponse<>(jwt);
        } catch (final BadCredentialException ex) {
            return MessageResponse.ErrorWithCode(ex.getMessage(), 400);
        }
    }

    @PostMapping("/forgot-password")
    public MessageResponse<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest payload) {
        try {
            authenticationService.requestForgotPassword(payload);
            return new MessageResponse<>("Your reset password link has been sent to your email");
        } catch (final Exception e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), 500);
        }
    }


}
