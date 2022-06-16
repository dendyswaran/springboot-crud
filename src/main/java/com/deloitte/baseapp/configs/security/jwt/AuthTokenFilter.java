package com.deloitte.baseapp.configs.security.jwt;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deloitte.baseapp.configs.security.services.UserDetailsServiceImpl;
import com.deloitte.baseapp.modules.account.entities.User;
import com.deloitte.baseapp.modules.account.entities.UserToken;
import com.deloitte.baseapp.modules.account.entities.UserTokenRedis;
import com.deloitte.baseapp.modules.account.repositories.UserRepository;
import com.deloitte.baseapp.modules.account.repositories.UserTokenRedisRepository;
import com.deloitte.baseapp.modules.account.repositories.UserTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserTokenRepository userTokenRepository;

  @Autowired
  private UserTokenRedisRepository userTokenRedisRepository;

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String jwt = parseJwt(request);
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        String username = jwtUtils.getUserNameFromJwtToken(jwt);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        //compare token
        Boolean accessGranted = true;
        Optional<User> getUser = userRepository.findByUsername(username);

        User user = getUser.get();

//        //compare token stored in DB
//        Optional<UserToken> getUserToken = userTokenRepository.findById(user.getId());
//        if(!getUserToken.isEmpty()){
//          UserToken userToken = getUserToken.get();
//          if(!jwt.equalsIgnoreCase(userToken.getToken())){
//            accessGranted = false;
//          }
//        }

        //compare token stored in redis
        Optional<UserTokenRedis> getUserTokenRedis = userTokenRedisRepository.findById(user.getId());
        if(!getUserTokenRedis.isEmpty()){
          UserTokenRedis userTokenRedis = getUserTokenRedis.get();
          if(!jwt.equalsIgnoreCase(userTokenRedis.getToken())){
            accessGranted = false;
          }
        }

        if(accessGranted) {
          UsernamePasswordAuthenticationToken authentication =
                  new UsernamePasswordAuthenticationToken(
                          userDetails,
                          null,
                          userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    } catch (Exception e) {
      logger.error("Cannot set user authentication: {}", e);
    }

    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7, headerAuth.length());
    }

    return null;
  }
}
