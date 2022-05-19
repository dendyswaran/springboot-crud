package com.deloitte.baseapp.modules.manageUser.payloads;

import com.deloitte.baseapp.commons.GenericEntity;
import com.deloitte.baseapp.modules.account.entities.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserResponse implements GenericEntity<UserResponse> {
    private Long userId;
    private String username;
    private String email;
    private Set<Role> roles;


    public UserResponse(Long userId, String username, String email, Set<Role> roles) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public UserResponse() {

    }

    @Override
    public void update(UserResponse source) {
        this.username = source.getUsername();
        this.email = source.getEmail();
        this.roles = source.getRoles();
    }

    @Override
    public Long getId() {
        return this.userId;
    }

    @Override
    public UserResponse createNewInstance() {
        UserResponse newInstance = new UserResponse();
        newInstance.update(this);
        return newInstance;
    }
}
