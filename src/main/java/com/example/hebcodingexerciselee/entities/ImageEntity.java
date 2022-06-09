package com.example.hebcodingexerciselee.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "\"images\"")
public class ImageEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "filename")
    private String filename;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "type")
    private String type;

    @Column(name = "source")
    private byte[] source;

    @Column(columnDefinition = "text[]")
    @Type(type = "com.example.hebcodingexerciselee.entities.CustomStringArrayType")
    private String[] objects;

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

    public String[] getObjects() { return objects; }

    public void setObjects(String[] objects) { this.objects = objects; }

}