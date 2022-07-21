package com.deloitte.baseapp.modules.file.services;

import com.deloitte.baseapp.modules.file.entitites.BaseConfig;
import com.deloitte.baseapp.modules.file.repositories.BaseConfigRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.*;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
@Configurable
public class FileWatcherListener implements FileChangeListener {

    @Autowired
    FileService fileService;

    private static FileService fileServicePost;

    @PostConstruct
    public void init() {
        FileWatcherListener.fileServicePost = fileService;
    }

    @SneakyThrows
    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        for(ChangedFiles cfiles : changeSet) {
            for(ChangedFile cfile: cfiles.getFiles()) {
                log.info("Operation: " + cfile.getType()
                        + " On file: " + cfile.getFile().getName() + " is done");

                if(cfile.getType().toString().equalsIgnoreCase("ADD")){
                    fileServicePost.moveAndConvertToTxt(cfile.getFile().getName());
                }
            }
        }
    }

//    private boolean isLocked(Path path) {
//        try (FileChannel ch = FileChannel.open(path, StandardOpenOption.WRITE); FileLock lock = ch.tryLock()) {
//            return lock == null;
//        } catch (IOException e) {
//            return true;
//        }
//    }

}
