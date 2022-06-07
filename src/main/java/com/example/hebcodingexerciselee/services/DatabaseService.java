package com.example.hebcodingexerciselee.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {
    @Autowired
    DatabaseService(){}

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insertImagesToPostgres() {
        String sql = String.format("INSERT INTO public.\"Images\"(id, filename, type, source, objects) VALUES (?, ?, ?, ?, ?)");

        jdbcTemplate.update("INSERT INTO public.\"Images\"(id, filename, type, source, objects) VALUES (?, ?, ?, ?, ?)");
    }
}
