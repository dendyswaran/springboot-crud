package com.deloitte.baseapp.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class QueryFileReader {

    public String read(final String relFilePath) throws Exception {
        final String sqlQuery = readFile(relFilePath);

        return sqlQuery;
    }

    private String readFile(final String relFilePath) throws Exception {
        final File resource = new ClassPathResource("dialects/" + relFilePath).getFile();
        if (!resource.exists()) {
            throw new Exception("File not found");
        }

        final String text = new String(Files.readAllBytes(resource.toPath()));
        return text;
    }


}
