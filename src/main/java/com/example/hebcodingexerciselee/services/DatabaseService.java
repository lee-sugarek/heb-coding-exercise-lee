package com.example.hebcodingexerciselee.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseService {
    @Autowired
    DatabaseService(){}

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insertImagesToPostgres() throws IOException {
        var imgFile = new ClassPathResource("stored_images/bicycle.jpg");
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
        List<String> objects = new ArrayList<>();
        objects.add("bicycle");
        objects.add("wheel");
        objects.add("tire");

        Array sqlArray = jdbcTemplate.execute(
                (Connection c) -> c.createArrayOf(JDBCType.VARCHAR.getName(), objects.toArray()));


        PreparedStatementSetter ps = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, 4);
                ps.setString(2, "bicycle.jpg");
                ps.setString(3, "image/jpg");
                ps.setBytes(4, bytes);
                ps.setArray(5, sqlArray);
            }
        };

        jdbcTemplate.update("INSERT INTO public.\"Images\"(id, filename, type, source, objects) VALUES (?, ?, ?, ?, ?)", ps);
    }
}
