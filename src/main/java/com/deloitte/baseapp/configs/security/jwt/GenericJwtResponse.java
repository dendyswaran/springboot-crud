package com.deloitte.baseapp.configs.security.jwt;

import lombok.Data;

import java.util.Set;

@Data
public class GenericJwtResponse<T,ID> {
    private String token;
    private String type = "Bearer";
    private ID id;
    private String username;
    private String email;
    private Set<T> rolesId;

    public GenericJwtResponse(String accessToken, ID id, String username, String email, Set<T> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.rolesId = roles;
    }

}
