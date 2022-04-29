package com.deloitte.baseapp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class QueryFileReader<T> {

    private JdbcTemplate jdbcTemplate;
    private Class<T> clazz;

    public QueryFileReader(JdbcTemplate jdbcTemplate, Class<T> clazz) {
        this.jdbcTemplate = jdbcTemplate;
        this.clazz = clazz;
    }

    public List<T> queryMulti(final String relFilePath) throws Exception {
        final String sqlQuery = readFile(relFilePath);
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<T>(clazz));
    }

    public List<T> queryMulti(final String relFilePath, final PreparedStatementSetter args) throws Exception {
        final String sqlQuery = readFile(relFilePath);
        return jdbcTemplate.query(sqlQuery, args, new BeanPropertyRowMapper<T>(clazz));
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
