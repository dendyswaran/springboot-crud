package com.deloitte.baseapp.configs.security.services;

import com.deloitte.baseapp.modules.tAccount.entities.OrgUser;
import com.deloitte.baseapp.modules.tAccount.repositories.TOrgUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrgUserDetailsServiceImpl implements UserDetailsService {
    TOrgUserRepository repository;

    public OrgUserDetailsServiceImpl (TOrgUserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OrgUser user =  repository.findByName(username)
                .orElseThrow(
                        ()-> new UsernameNotFoundException("User with username" + username + " cannot be found")
                );
        return OrgUserDetailsImpl.build(user);
    }
}
