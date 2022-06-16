package com.deloitte.baseapp.modules.authentication.services;

import com.deloitte.baseapp.commons.LogInfo;
import com.deloitte.baseapp.configs.security.jwt.JwtResponse;
import com.deloitte.baseapp.configs.security.jwt.JwtUtils;
import com.deloitte.baseapp.modules.account.entities.Role;
import com.deloitte.baseapp.modules.account.entities.User;
import com.deloitte.baseapp.modules.account.entities.UserToken;
import com.deloitte.baseapp.modules.account.entities.UserTokenRedis;
import com.deloitte.baseapp.modules.account.exceptions.RoleNotFoundException;
import com.deloitte.baseapp.modules.account.repositories.UserRepository;
import com.deloitte.baseapp.modules.account.repositories.UserTokenRedisRepository;
import com.deloitte.baseapp.modules.account.repositories.UserTokenRepository;
import com.deloitte.baseapp.modules.account.services.RoleService;
import com.deloitte.baseapp.modules.authentication.exception.BadCredentialException;
import com.deloitte.baseapp.modules.authentication.exception.EmailHasBeenUsedException;
import com.deloitte.baseapp.modules.authentication.payloads.ForgotPasswordRequest;
import com.deloitte.baseapp.modules.authentication.payloads.SigninRequest;
import com.deloitte.baseapp.modules.authentication.payloads.SignupRequest;
import com.deloitte.baseapp.modules.notification.payloads.EmailRequest;
import com.deloitte.baseapp.modules.notification.services.NotificationEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class AuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    NotificationEmailService notificationEmailService;

    @Autowired
    UserTokenRepository userTokenRepository;

    @Autowired
    UserTokenRedisRepository userTokenRedisRepository;

    /**
     * Registers a user by using email and password
     *
     * @param payload
     * @return
     */
    public User signup(final SignupRequest payload) throws EmailHasBeenUsedException, RoleNotFoundException {
        final Boolean exists = userRepository.existsByEmail(payload.getEmail());
        if (exists)
            throw new EmailHasBeenUsedException();

        User user = new User();
        user.setUsername(payload.getUsername());
        user.setEmail(payload.getEmail());
        user.setPassword(encoder.encode(payload.getPassword()));

        // check roles
        Set<Role> userRoles = new HashSet<>();
        for (String role : payload.getRoles()) {
            final Role _role = roleService.getRoleByName(role);
            if (null != _role) {
                userRoles.add(_role);
            }
        }

        user.setRoles(userRoles);
        userRepository.save(user);
        log.info("User has been saved: " + user.getId());

        return user;
    }

    /**
     *
     * @param payload
     * @return
     * @throws BadCredentialException
     */
    public JwtResponse signin(final SigninRequest payload) throws BadCredentialException {
        final Optional<User> optionalUser = userRepository.findByUsername(payload.getUsername());
        if (optionalUser.isEmpty())
            throw new BadCredentialException();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String accessToken = jwtUtils.generateJwtToken(authentication);
        final User user = optionalUser.get();

        //STORE VALID USER TOKEN
        storeValidToken(user.getId(), accessToken);

        return new JwtResponse(accessToken,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles());
    }

    /**
     * Sending forgot password link through email
     *
     * @param payload
     */
    public void requestForgotPassword(final ForgotPasswordRequest payload) {
        Optional<User> optionalUser = userRepository.findByEmail(payload.getEmail());
        if (!optionalUser.isEmpty()) {
            final User user = optionalUser.get();

            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setRecipient(user.getEmail());
            emailRequest.setBody("Forgot Password Body"); //TODO:
            emailRequest.setSubject("Forgot Password");   //TODO:

            notificationEmailService.sendEmail(emailRequest);
        }
    }

    private void storeValidToken(Long userId, String accessToken) {
        //STORE INTO DB
//        UserToken userToken = new UserToken();
//        userToken.setUser_id(userId);
//        userToken.setToken(accessToken);
//
//        userTokenRepository.save(userToken);

        //STORE INTO REDIS
        UserTokenRedis userTokenRedis = new UserTokenRedis();
        userTokenRedis.setUserId(userId);
        userTokenRedis.setToken(accessToken);

        userTokenRedisRepository.save(userTokenRedis);

    }
}
