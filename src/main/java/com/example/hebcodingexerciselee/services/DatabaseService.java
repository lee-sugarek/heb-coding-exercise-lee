package com.example.hebcodingexerciselee.services;

import com.example.hebcodingexerciselee.dtos.ImageDto;
import com.example.hebcodingexerciselee.entities.ImageEntity;
import com.example.hebcodingexerciselee.repositories.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.*;
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

    public List<ImageDto> getImagesByObjects(List<String> objects) {

        StringBuilder sql = new StringBuilder("SELECT * FROM public.\"images\" ");
        if (objects.size() > 0) {
            sql.append("WHERE ? = ANY(objects) ");
            sql.append("OR ? = ANY(objects) ".repeat(objects.size() - 1));
        }

        PreparedStatementSetter ps = ps1 -> {
            for(int i = 1; i <= objects.size(); i++) {
                ps1.setString(i, objects.get(i-1));
            }
        };
        RowMapper<ImageDto> rowMapper = new ImageMapper();

        return jdbcTemplate.query(sql.toString(), ps, rowMapper);
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

    public static class ImageMapper implements RowMapper<ImageDto> {
        @Override
        public ImageDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ImageDto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBytes(4), rs.getArray(5));
        }
    }
}
