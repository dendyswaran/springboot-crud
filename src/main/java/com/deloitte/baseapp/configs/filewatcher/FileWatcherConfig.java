package com.deloitte.baseapp.configs.filewatcher;

import com.deloitte.baseapp.modules.file.entitites.BaseConfig;
import com.deloitte.baseapp.modules.file.repositories.BaseConfigRepository;
import com.deloitte.baseapp.modules.file.services.FileWatcherListener;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.io.File;
import java.time.Duration;
import java.util.Optional;

@Slf4j
@Configuration
public class FileWatcherConfig {

    @Autowired
    BaseConfigRepository baseConfigRepository;

    @Value("${deloitte.app.incomingDirConfig}")
    private String incomingDirPath;
    @Bean
    public FileSystemWatcher fileSystemWatcher() {
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.ofMillis(5000L), Duration.ofMillis(3000L));
        Optional<BaseConfig> getIncomingDirConfig = baseConfigRepository.findById(incomingDirPath);
        BaseConfig incomingDirConfig = getIncomingDirConfig.get();
        fileSystemWatcher.addSourceDirectory(new File(incomingDirConfig.getContent()));
        fileSystemWatcher.addListener(new FileWatcherListener());
        fileSystemWatcher.start();
        log.info("File watcher started");

        return fileSystemWatcher;
    }

    @PreDestroy
    public void onDestroy() throws Exception {
        fileSystemWatcher().stop();
    }
}
