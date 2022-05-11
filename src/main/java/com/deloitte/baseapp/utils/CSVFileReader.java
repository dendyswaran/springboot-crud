package com.deloitte.baseapp.utils;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CSVFileReader<T> {

    public List<T> readFromFile(InputStream file, Class<T> clazz) throws Exception {
        final Reader reader = new BufferedReader(new InputStreamReader(file));

        final CsvToBean<T> csvToBean = new CsvToBeanBuilder(reader)
                .withType(clazz)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        return csvToBean.parse();
    }

    public List<T> readFromFolder(Class<T> clazz, ICSVFileOnRead<T> onRead) throws Exception {
        final File file = this.readFile();

        if (file.isDirectory()) {
            this.readFilesInFolder(file, clazz, onRead);
        }

        return null;
    }

    private File readFile() throws Exception {
        final File resource = new ClassPathResource("files").getFile();
        if (!resource.exists()) {
            throw new Exception("File not found");
        }

        return resource;
    }

    private void readFilesInFolder(final File folder, Class<T> clazz, ICSVFileOnRead<T> onRead) throws Exception {
        for (File _file : folder.listFiles()) {
            if (_file.isFile()) {
                final InputStream inputStream = new FileInputStream(_file);
                final List<T> resultsFromFile = this.readFromFile(inputStream, clazz);

                onRead.onData(resultsFromFile);
            }
        }
    }

}
