package com.deloitte.baseapp.modules.tasklist;

import com.deloitte.baseapp.modules.tasklist.entities.TMtMake;
import com.deloitte.baseapp.modules.tasklist.entities.TMtWfStatus;
import com.deloitte.baseapp.modules.tasklist.object.TMtMakeCd;
import com.deloitte.baseapp.modules.tasklist.object.TMtWfStatusCd;
import com.deloitte.baseapp.modules.tasklist.repositories.TMtMakeRepository;
import com.deloitte.baseapp.modules.tasklist.repositories.TMtWfStatusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
// https://stackoverflow.com/questions/60606861/spring-boot-jpa-metamodel-must-not-be-empty-when-trying-to-run-junit-integrat/60608499#60608499
public class AppConfig {

    @Autowired
    private TMtMakeRepository tMtMakeRepository;

    @Autowired
    private TMtWfStatusRepository tMtWfStatusRepository;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Bean
    public CommandLineRunner TMtMakeDataLoader(TMtMakeRepository repo) {
        int version = 0;

        // TODO: how to handle "Others".
        return args -> {
            repo.save(new TMtMake(1L, TMtMakeCd.COM_POWER, "Com-Power", "Jun Kang", "Jun Kang", version));
            repo.save(new TMtMake(2L, TMtMakeCd.ERICSSON, "Ericsson", "Jun Kang", "Jun Kang", version));
            repo.save(new TMtMake(3L, TMtMakeCd.NOKIA, "Nokia", "Jun Kang", "Jun Kang", version));
            repo.save(new TMtMake(4L, TMtMakeCd.TELEVES, "Televes", "Jun Kang", "Jun Kang", version));
            repo.save(new TMtMake(5L, TMtMakeCd.OTHERS, "Others", "Jun Kang", "Jun Kang", version));
        };
    }


    @Bean
    public CommandLineRunner TMtWfStatusDataLoader(TMtWfStatusRepository repo) {
        int version = 0;

        return args -> {
            repo.save(new TMtWfStatus(1L, TMtWfStatusCd.NEW, "New", "Jun Kang", "Jun Kang", version));
            repo.save(new TMtWfStatus(2L, TMtWfStatusCd.PENDING_DECOM, "Pending Decom", "Jun Kang", "Jun Kang", version));
            repo.save(new TMtWfStatus(3L, TMtWfStatusCd.PENDING_SCRAP_OEM, "Pending Scrap/OEM", "Jun Kang", "Jun Kang", version));
            repo.save(new TMtWfStatus(4L, TMtWfStatusCd.PARTIAL_COMPLETE, "Partial Complete", "Jun Kang", "Jun Kang", version));
            repo.save(new TMtWfStatus(5L, TMtWfStatusCd.COMPLETED, "Completed", "Jun Kang", "Jun Kang", version));
        };
    }
}
