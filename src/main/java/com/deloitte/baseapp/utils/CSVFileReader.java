package com.deloitte.baseapp.utils;

import com.deloitte.baseapp.modules.menu.entities.Menu;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class CSVFileReader<T> {

    public List<T> readFromFile(MultipartFile file, Class<T> clazz) throws Exception {
        final Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        final CsvToBean<T> csvToBean = new CsvToBeanBuilder(reader)
                .withType(clazz)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        return csvToBean.parse();
    }

}
