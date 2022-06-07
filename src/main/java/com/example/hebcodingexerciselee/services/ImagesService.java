package com.example.hebcodingexerciselee.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.List;

@Service
public class ImagesService {
    private final VisionService visionService;
    private final DatabaseService databaseService;

    @Autowired
    public ImagesService(final VisionService visionService,
                         final DatabaseService databaseService) {
        this.visionService = visionService;
        this.databaseService = databaseService;
    }

    public ResponseEntity<byte[]> getImages() throws IOException {

        //databaseService.insertImagesToPostgres();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=%s", "Apple.jpeg"));

        var imgFile = new ClassPathResource("stored_images/apple.jpg");
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }

    public ResponseEntity<byte[]> getImagesByObjects(List<String> objects) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=%s", "Apple.jpeg"));

        var imgFile = new ClassPathResource("stored_images/banana.jpg");
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }

    public ResponseEntity<byte[]> getImagesById(String imageId) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=%s", "Apple.jpeg"));

        var imgFile = new ClassPathResource("stored_images/cranberries.jpg");
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }

    public String postImages() {
        return "Post Images API call";
    }
}
