package com.deloitte.baseapp.configs.security.jwt;

import com.deloitte.baseapp.modules.account.entities.Role;
import lombok.Data;

import java.util.Set;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private Set<Role> roles;

    public JwtResponse(String accessToken, Long id, String username, String email, Set<Role> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

}
