package com.deloitte.baseapp.modules.file.controllers;

import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.modules.authentication.exception.BadCredentialException;
import com.deloitte.baseapp.modules.authentication.services.AuthenticationService;
import com.deloitte.baseapp.modules.file.exception.FileStorageException;
import com.deloitte.baseapp.modules.file.payloads.UploadFileResponse;
import com.deloitte.baseapp.modules.file.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        try {
            return Arrays.asList(files)
                    .stream()
                    .map(file -> fileService.uploadFile(file))
                    .collect(Collectors.toList());
        } catch (final FileStorageException ex) {
            return (List<UploadFileResponse>) MessageResponse.ErrorWithCode(ex.getMessage(), 400);
        }
    }
}
