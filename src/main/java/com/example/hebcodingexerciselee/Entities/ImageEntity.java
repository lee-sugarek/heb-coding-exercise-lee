package com.example.hebcodingexerciselee.Entities;

import javax.persistence.*;

@Entity
@Table(name = "\"Images\"")
public class ImageEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "filename")
    private String filename;

    @Lob
    @Column(name = "type")
    private String type;

    @Column(name = "source")
    private byte[] source;

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

/*
  TODO [JPA Buddy] create field to map the 'objects' column
   Available actions: Define target Java type | Uncomment as is | Remove column mapping
  @Column(name = "objects", columnDefinition = "varchar[]")
  private Object objects;
*/
}