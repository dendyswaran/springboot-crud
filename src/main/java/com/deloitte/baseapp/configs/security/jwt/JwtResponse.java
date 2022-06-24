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

    private Long idleTimeoutMs;

    private Long gracePeriodMs;

    public JwtResponse(String accessToken, Long id, String username, String email, Set<Role> roles,
                       Long idleTimeoutMs, Long gracePeriodMs) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.idleTimeoutMs = idleTimeoutMs;
        this.gracePeriodMs = gracePeriodMs;
    }

}
