package com.example.hebcodingexerciselee.services;

import com.example.hebcodingexerciselee.dtos.ImageDto;
import com.example.hebcodingexerciselee.entities.ImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
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

    public List<ImageDto> getImages() {
        List<ImageEntity> entities = databaseService.getImages();
        List<ImageDto> dtos = new ArrayList<>();
        entities.forEach(imageEntity -> dtos.add(convertImageEntityToDto(imageEntity)));

        return dtos;
    }

    public List<ImageDto> getImagesByObjects(String objects) {
        if (objects.length() > 2) {
            objects = objects.substring(1, objects.length() - 1);
        }

        return databaseService.getImagesByObjects(Arrays.stream(objects.split(",")).toList());
    }

    public ImageDto getImagesById(Integer imageId) {
        ImageEntity entity = databaseService.getById(imageId);
        return convertImageEntityToDto(entity);
    }

    public Integer postImages(MultipartFile multipartFile) throws Exception {
        ImageDto imageDto = new ImageDto();
        imageDto.setFilename(multipartFile.getOriginalFilename());
        imageDto.setSource(multipartFile.getBytes());
        imageDto.setType(multipartFile.getContentType());
        imageDto.setObjects(visionService.detectObjects(multipartFile.getBytes()));

        return databaseService.insertImage(imageDto);
    }

    private ImageDto convertImageEntityToDto(ImageEntity entity) {
        ImageDto dto = new ImageDto();
        dto.setId(entity.getId());
        dto.setFilename(entity.getFilename());
        dto.setType(entity.getType());
        dto.setSource(entity.getSource());
        if (entity.getObjects() != null) {
            dto.setObjects(Arrays.stream(entity.getObjects()).toList());
        }

        return dto;
    }
}
