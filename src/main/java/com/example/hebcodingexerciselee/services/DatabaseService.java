package com.example.hebcodingexerciselee.services;

import com.example.hebcodingexerciselee.dtos.ImageDto;
import com.example.hebcodingexerciselee.entities.ImageEntity;
import com.example.hebcodingexerciselee.repositories.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseService {
    private final ImagesRepository imagesRepository;
    @Autowired
    DatabaseService(final ImagesRepository imagesRepository){
        this.imagesRepository = imagesRepository;
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ImageEntity> getImages() { return imagesRepository.findAll(); }

    public List<ImageEntity> getImagesByObjects(List<String> objects) {
        List<ImageEntity> entities = new ArrayList<>();

        return entities;
    }

    public ImageEntity getById(Integer imageId) {
        return imagesRepository.getById(imageId);
    }

    public Integer insertImage(ImageDto dto) {
        Integer id = this.imagesRepository.findMaxId();

        int finalId = id == null ? 1 : id + 1;

        Array sqlArray = jdbcTemplate.execute(
                (Connection c) -> c.createArrayOf(JDBCType.VARCHAR.getName(), dto.getObjects().toArray()));

        PreparedStatementSetter ps = ps1 -> {
            ps1.setInt(1, finalId);
            ps1.setString(2, dto.getFilename());
            ps1.setString(3, dto.getType());
            ps1.setBytes(4, dto.getSource());
            ps1.setArray(5, sqlArray);
        };

        jdbcTemplate.update("INSERT INTO public.\"images\"(id, filename, type, source, objects) VALUES (?, ?, ?, ?, ?)", ps);

        return finalId;
    }
}
