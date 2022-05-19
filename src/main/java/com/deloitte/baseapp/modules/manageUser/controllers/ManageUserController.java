package com.deloitte.baseapp.modules.manageUser.controllers;

import com.deloitte.baseapp.commons.GenericController;
import com.deloitte.baseapp.commons.GenericRepository;
import com.deloitte.baseapp.modules.account.entities.User;
import com.deloitte.baseapp.modules.account.repositories.UserRepository;
import com.deloitte.baseapp.modules.manageUser.payloads.UserResponse;
import com.deloitte.baseapp.modules.manageUser.services.ManageUserService;
import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.configs.security.services.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/manage-user")
public class ManageUserController extends GenericController<User> {

    final ManageUserService manageUserService;

    public ManageUserController(GenericRepository<User> repository, ManageUserService manageUserService) {
        super(repository, "manageUser");
        this.manageUserService = manageUserService;
    }

    @GetMapping("/user-list")
    public MessageResponse<?> getUserList() {
        final UserDetailsImpl userPrincipal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<UserResponse> users = manageUserService.getAllUser();
        return new MessageResponse<>(users);
    }
}
