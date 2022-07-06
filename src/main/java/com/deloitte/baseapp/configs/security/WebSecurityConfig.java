package com.deloitte.baseapp.configs.security;

import com.deloitte.baseapp.configs.security.jwt.AuthEntryPointJwt;
import com.deloitte.baseapp.configs.security.jwt.AuthTokenFilter;
import com.deloitte.baseapp.configs.security.services.OrgUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
         prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    final
    OrgUserDetailsServiceImpl userDetailsService;

    private final AuthEntryPointJwt unauthorizedHandler;

    public WebSecurityConfig(OrgUserDetailsServiceImpl userDetailsService, AuthEntryPointJwt unauthorizedHandler) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/org-auth/**").permitAll()
                .antMatchers("/api/org-group/**").permitAll()
                .antMatchers("/api/mt-status/**").permitAll()
                .antMatchers("/api/org-team/**").permitAll()
                .antMatchers("/api/org-user/**").authenticated()
                .antMatchers("/api/manage-user/**").authenticated()
                .authorizeRequests().antMatchers("/api/auth/**", "/api/tasklist/**", "/api/ioh-tasklist/**",
                        "/api/ioh-tasklist/**/**", "/api/decom-tasklist/**", "/api/decom-tasklist/**/**", "/api/warehouse-assignment/**").permitAll()
                .antMatchers("/api/test/**").permitAll()
                .antMatchers("/api/org/**").permitAll()
                .anyRequest().authenticated();


        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}