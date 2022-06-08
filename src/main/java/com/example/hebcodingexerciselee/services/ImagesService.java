package com.example.hebcodingexerciselee.services;

import com.example.hebcodingexerciselee.dtos.ImageDto;
import com.example.hebcodingexerciselee.entities.ImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
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

    public ImageDto getImagesById(Integer imageId) throws IOException {
        ImageEntity entity = databaseService.findById(imageId);

        return convertImageEntityToDto(entity);
    }

    public Integer postImages(MultipartFile multipartFile) throws Exception {
        ImageDto imageDto = new ImageDto();
        imageDto.setFilename(multipartFile.getOriginalFilename());
        imageDto.setSource(multipartFile.getBytes());
        imageDto.setType(multipartFile.getContentType());
        imageDto.setObjects(visionService.detectObjects(multipartFile.getBytes()));

        return databaseService.insertImageToPostgres(imageDto);
    }


    private ImageDto convertImageEntityToDto(ImageEntity entity) throws IOException {
        ImageDto dto = new ImageDto();
        dto.setId(entity.getId());
        dto.setFilename(entity.getFilename());
        dto.setType(entity.getType());
        var imgFile = new ClassPathResource("stored_images/bicycle.jpg");
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
        dto.setSource(bytes);

        return dto;
    }
}
