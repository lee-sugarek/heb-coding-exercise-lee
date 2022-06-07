package com.example.hebcodingexerciselee.controllers;

import com.example.hebcodingexerciselee.services.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/images")
@RestController
public class ImagesController {

    private final ImagesService imagesService;

    @Autowired
    public ImagesController(final ImagesService imagesService) {
        this.imagesService = imagesService;
    }

//    @GetMapping
//    public String getImages() {
//        return imagesService.getImages();
//    }

    @GetMapping
    public ResponseEntity<byte[]> getImages() throws IOException {
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

    @GetMapping(params = "objects")
    public String getImagesByObject(@RequestParam(required = false) List<String> objects) {
        return imagesService.getImagesByObjects(objects);
    }

    @GetMapping(value = "/{imageId}")
    public String getImagesById(@PathVariable final String imageId) {
        return imagesService.getImagesById(imageId);
    }

    @PostMapping
    public String postImages() {
        return imagesService.postImages();
    }
}