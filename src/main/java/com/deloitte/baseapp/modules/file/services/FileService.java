package com.deloitte.baseapp.modules.file.services;

import com.deloitte.baseapp.modules.file.entitites.BaseConfig;
import com.deloitte.baseapp.modules.file.exception.FileStorageException;
import com.deloitte.baseapp.modules.file.payloads.UploadFileResponse;
import com.deloitte.baseapp.modules.file.repositories.BaseConfigRepository;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Slf4j
@Service
public class FileService {

    @Autowired
    BaseConfigRepository baseConfigRepository;

    @Value("${deloitte.app.incomingDirConfig}")
    private String incomingDirPath;

    @Value("${deloitte.app.outcomingDirConfig}")
    private String outcomingDirPath;

    public String storeFile(MultipartFile file) throws FileStorageException {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //fileName = StringUtils.replace(fileName,".csv", ".txt");

        try {
            // Check if the file name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Optional<BaseConfig> getIncomingDirConfig = baseConfigRepository.findById(incomingDirPath);
            BaseConfig incomingDirConfig = getIncomingDirConfig.get();

            Path fileStorageLocation = Paths.get(incomingDirConfig.getContent())
                    .toAbsolutePath().normalize();

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public UploadFileResponse uploadFile(MultipartFile file) throws FileStorageException {
        String fileName = storeFile(file);

        return new UploadFileResponse(fileName,
                file.getContentType(), file.getSize());
    }

    public void moveAndConvertToTxt(String fileName) throws IOException {
        String toFileName = StringUtils.replace(fileName,".csv", ".txt");

        try{
            Optional<BaseConfig> getIncomingDirConfig = baseConfigRepository.findById(incomingDirPath);
            BaseConfig incomingDirConfig = getIncomingDirConfig.get();

            Optional<BaseConfig> getOutcomingDirConfig = baseConfigRepository.findById(outcomingDirPath);
            BaseConfig outcomingDirConfig = getOutcomingDirConfig.get();

            //get file from source location
            Path incomingDirLocation = Paths.get(incomingDirConfig.getContent())
                    .toAbsolutePath().normalize();
            Path sourceLocation = incomingDirLocation.resolve(fileName);

            //move file to target location
            Path outcomingDirLocation = Paths.get(outcomingDirConfig.getContent())
                    .toAbsolutePath().normalize();
            Path targetLocation = outcomingDirLocation.resolve(toFileName);

            Files.move(sourceLocation, targetLocation);

            log.info(fileName + " converted successfully and moved to outcoming folder");
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
