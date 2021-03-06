package com.example.hebcodingexerciselee.dtos;

import java.sql.Array;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/* Data transfer object that contains image metadata and is transferred to the client */
public class ImageDto {
    private Integer id;
    private String filename;
    private String type;
    private byte[] source;
    private List<String> objects;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getSource() {
        return source;
    }

    public void setSource(byte[] source) {
        this.source = source;
    }

    public List<String> getObjects() {
        return objects;
    }

    public void setObjects(List<String> objects) { this.objects = objects; }

    public ImageDto(){}

    public ImageDto(Integer id, String filename, String type, byte[] source, Array objects) throws SQLException {
        this.id = id;
        this.filename = filename;
        this.type = type;
        this.source = source;
        this.objects = Arrays.stream((String[])objects.getArray()).toList();
    }
}
