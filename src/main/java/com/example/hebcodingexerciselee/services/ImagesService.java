package com.example.hebcodingexerciselee.services;

import com.example.hebcodingexerciselee.Entities.ImageEntity;
import com.example.hebcodingexerciselee.Repositories.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ImagesService {
    private final VisionService visionService;
    private final DatabaseService databaseService;
    private final ImagesRepository imagesRepository;

    @Autowired
    public ImagesService(final VisionService visionService,
                         final DatabaseService databaseService,
                         final ImagesRepository imagesRepository) {
        this.visionService = visionService;
        this.databaseService = databaseService;
        this.imagesRepository = imagesRepository;
    }

    public ResponseEntity<byte[]> getImages() throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=%s", "apple.jpeg"));

        var imgFile = new ClassPathResource("stored_images/apple.jpg");
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }

    public ResponseEntity<byte[]> getImagesByObjects(List<String> objects) throws IOException, SQLException {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=%s", "banana.jpeg"));

        var imgFile = new ClassPathResource("stored_images/banana.jpg");
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }

    public ResponseEntity<byte[]> getImagesById(Integer imageId) throws IOException, SQLException {
        ImageEntity entityPlaceholder = new ImageEntity();
        ImageEntity entity = imagesRepository.findById(imageId).orElse(entityPlaceholder);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=%s", entity.getFilename()));

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.IMAGE_JPEG)
                .body(entity.getSource());
    }

    public String postImages() {
        return "Post Images API call";
    }

    public void insertImages() throws SQLException, IOException {

        databaseService.insertImagesToPostgres();
    }
}
