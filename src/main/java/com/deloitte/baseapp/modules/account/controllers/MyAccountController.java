package com.deloitte.baseapp.modules.account.controllers;

import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.configs.security.services.UserDetailsImpl;
import com.deloitte.baseapp.modules.account.entities.User;
import com.deloitte.baseapp.modules.account.exceptions.UserNotFoundException;
import com.deloitte.baseapp.modules.account.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/my-account")
public class MyAccountController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public MessageResponse<?> getMyProfile() {
        try {
            final UserDetailsImpl userPrincipal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final User user = userService.getProfileByEmail(userPrincipal.getEmail());

            return new MessageResponse<>(user, "Success");
        } catch (final UserNotFoundException ex) {
            return new MessageResponse<>(null, ex.getMessage());
        }
    }

}
