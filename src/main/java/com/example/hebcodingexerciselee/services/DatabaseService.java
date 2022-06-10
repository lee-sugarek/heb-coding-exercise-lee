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

/*
The database service builds and executes all the sql queries.
JPA repository used for executing the simple queries and then jdbcTemplate for the more complex custom queries.
 */
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

    /* This method builds a SQL query and returns all images if no objects are passed in.
       If there is one object present, then we append the WHERE clause.
       For every other object, we append an OR clause.

       We then loop through and append each of these objects into the prepared statement.
     */
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

    public ImageDto insertImage(ImageDto dto) {

        Integer id = this.imagesRepository.findMaxId();
        // If no records have been inserted into DB, then we set id to 1. Otherwise, the unique id is set to the max id plus one.
        dto.setId(id == null ? 1 : id + 1);

        // Generate file name if none present
        if(dto.getFilename() == null) {
            String fileTypeEnding = dto.getType().substring(6);
            dto.setFilename(String.format("image%d.%s", dto.getId(), fileTypeEnding));
        }

        Array sqlArray = null;
        //Converts String List to java.sql.Array that can be inserted into Postgres Varying Character Array column type.
        if(dto.getObjects() != null) {
            sqlArray = jdbcTemplate.execute(
                    (Connection c) -> c.createArrayOf(JDBCType.VARCHAR.getName(), dto.getObjects().toArray()));
        }

        Array finalSqlArray = sqlArray;
        PreparedStatementSetter ps = ps1 -> {
            ps1.setInt(1, dto.getId());
            ps1.setString(2, dto.getFilename());
            ps1.setString(3, dto.getType());
            ps1.setBytes(4, dto.getSource());
            ps1.setArray(5, finalSqlArray);
        };

        jdbcTemplate.update("INSERT INTO public.\"images\"(id, filename, type, source, objects) VALUES (?, ?, ?, ?, ?)", ps);

        return dto;
    }

    /* Inner class that maps a resultSet to ImageDto */
    public static class ImageMapper implements RowMapper<ImageDto> {
        @Override
        public ImageDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ImageDto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBytes(4), rs.getArray(5));
        }
    }
}
