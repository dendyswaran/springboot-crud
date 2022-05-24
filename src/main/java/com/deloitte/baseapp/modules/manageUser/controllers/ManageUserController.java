package com.deloitte.baseapp.modules.manageUser.controllers;

import com.deloitte.baseapp.commons.GenericController;
import com.deloitte.baseapp.commons.GenericRepository;
import com.deloitte.baseapp.commons.ObjectNotFoundException;
import com.deloitte.baseapp.modules.account.entities.User;
import com.deloitte.baseapp.modules.account.repositories.UserRepository;
import com.deloitte.baseapp.modules.account.services.UserService;
import com.deloitte.baseapp.modules.manageUser.payloads.UserResponse;
import com.deloitte.baseapp.modules.manageUser.services.ManageUserService;
import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.configs.security.services.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manage-user")
@CrossOrigin(origins = "*")
public class ManageUserController extends GenericController<User> {

    final ManageUserService manageUserService;
    final UserService userService;

    public ManageUserController(GenericRepository<User> repository, UserService userService, ManageUserService manageUserService) {
        super(repository, "manageUser");
        this.userService = userService;
        this.manageUserService = manageUserService;
    }

    @GetMapping("/user-list")
    public MessageResponse<?> getUserList() {
        final UserDetailsImpl userPrincipal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<UserResponse> users = manageUserService.getAllUser();
        return new MessageResponse<>(users);
    }

    @PutMapping("/edit/{id}")
    public MessageResponse updateUser(@PathVariable("id") Long id, @RequestBody UserResponse values) {
        try {
            User updated = userService.get(id);
            updated.setUsername(values.getUsername());
            updated.setEmail(values.getEmail());
            return new MessageResponse(userService.update(id, updated));
        } catch (ObjectNotFoundException e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), e.getCode());
        }
    }
}
