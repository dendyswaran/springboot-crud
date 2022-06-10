package com.deloitte.baseapp.configs.security.services;

import com.deloitte.baseapp.modules.tAccount.entities.OrgUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class OrgUserDetailsImpl implements UserDetails {

    private UUID id;
    private String username;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public OrgUserDetailsImpl(UUID id,
                              String username,
                              String email,
                              String password,
                              Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static OrgUserDetailsImpl build(OrgUser user) {
        List<GrantedAuthority> authorities = user.getOrgUsrUsrGroups().stream()
                .map(orgUsrUsrGroup -> new SimpleGrantedAuthority(
                        orgUsrUsrGroup.getOrgUsrGroup().getName()
                        )).collect(Collectors.toList());
        return new OrgUserDetailsImpl(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrgUserDetailsImpl user = (OrgUserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
