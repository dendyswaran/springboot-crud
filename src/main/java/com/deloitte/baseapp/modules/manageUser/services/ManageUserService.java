package com.deloitte.baseapp.modules.manageUser.services;

import com.deloitte.baseapp.modules.account.entities.User;
import com.deloitte.baseapp.modules.account.repositories.UserRepository;
import com.deloitte.baseapp.modules.manageUser.payloads.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ManageUserService {
    @Autowired
    UserRepository userRepository;

    public List<UserResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userList = users.stream().map(user ->
                new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRoles()))
                .collect(Collectors.toList());
        return userList;
    }
}
