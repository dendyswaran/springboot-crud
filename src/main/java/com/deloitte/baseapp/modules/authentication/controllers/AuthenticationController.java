package com.deloitte.baseapp.modules.authentication.controllers;

import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.configs.security.jwt.JwtResponse;
import com.deloitte.baseapp.modules.account.entities.User;
import com.deloitte.baseapp.modules.account.exceptions.RoleNotFoundException;
import com.deloitte.baseapp.modules.authentication.exception.BadCredentialException;
import com.deloitte.baseapp.modules.authentication.exception.EmailHasBeenUsedException;
import com.deloitte.baseapp.modules.authentication.payloads.ForgotPasswordRequest;
import com.deloitte.baseapp.modules.authentication.payloads.IdleTimeoutRequest;
import com.deloitte.baseapp.modules.authentication.payloads.SigninRequest;
import com.deloitte.baseapp.modules.authentication.payloads.SignupRequest;
import com.deloitte.baseapp.modules.authentication.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

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
            final JwtResponse jwt = authenticationService.signin(payload);
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

    @PostMapping("/idle-timeout")
    public MessageResponse<?> idleTimeout(@Valid @RequestBody IdleTimeoutRequest payload) {
        try {
            authenticationService.idleTimeout(payload);
            return new MessageResponse<>("You have been logged out");
        } catch (final Exception e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), 500);
        }
    }

}
