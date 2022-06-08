package com.example.hebcodingexerciselee.controllers;

import com.example.hebcodingexerciselee.services.ImagesService;
import com.google.common.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;

@RequestMapping("/images")
@RestController
public class ImagesController {

    private final ImagesService imagesService;

    @Autowired
    public ImagesController(final ImagesService imagesService) {
        this.imagesService = imagesService;
    }

    @GetMapping
    public ResponseEntity<byte[]> getImages() throws IOException {
        return imagesService.getImages();
    }

    @GetMapping(params = "objects")
    public ResponseEntity<byte[]> getImagesByObject(@RequestParam(required = false) List<String> objects) throws Exception {
        return imagesService.getImagesByObjects(objects);
    }

    @GetMapping(value = "/{imageId}")
    public ResponseEntity<byte[]> getImagesById(@PathVariable final Integer imageId) throws IOException, SQLException {
        return imagesService.getImagesById(imageId);
    }

    @PostMapping
    public RedirectView postImages(@RequestBody MultipartFile multipartFile) throws SQLException, IOException {
        System.out.println("Posting endpoint");
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String uploadDir = Resources.getResource("stored_images").toString();
        Path uploadPath = Paths.get(uploadDir);

        System.out.println(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }

        return new RedirectView("/users", true);
    }
}