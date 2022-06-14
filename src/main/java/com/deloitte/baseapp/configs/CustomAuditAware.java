package com.deloitte.baseapp.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class CustomAuditAware {

    // Important class to help in automatically set the createdBy and lastModifiedBy
    @Bean
    public AuditorAware<String> auditorProvider () {
        return new AuditorAware<String>() {
            @Override
            public Optional<String> getCurrentAuditor() {
                return Optional.of("test");
            }
        };
    }
}
