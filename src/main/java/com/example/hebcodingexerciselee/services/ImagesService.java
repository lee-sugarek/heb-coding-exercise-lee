package com.example.hebcodingexerciselee.services;

import com.example.hebcodingexerciselee.dtos.ImageDto;
import com.example.hebcodingexerciselee.entities.ImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Service class that handles all the data processing.
 */
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
        List<ImageEntity> imageEntities = databaseService.getImages();
        List<ImageDto> imageDtos = new ArrayList<>();
        imageEntities.forEach(imageEntity -> imageDtos.add(convertImageEntityToDto(imageEntity)));

        return imageDtos;
    }

    public List<ImageDto> getImagesByObjects(String objects) {
        //Trim off beginning and ending Quotation Marks and then splits into list ("Foo,Bar" -> Foo,Bar -> {Foo, Bar})
        if (objects.length() > 2) {
            objects = objects.substring(1, objects.length() - 1);
        }

        return databaseService.getImagesByObjects(Arrays.stream(objects.split(",")).toList());
    }

    public ImageDto getImagesById(Integer imageId) {
        ImageEntity imageEntity = databaseService.getById(imageId);

        return convertImageEntityToDto(imageEntity);
    }

    public ImageDto postImages(MultipartFile multipartFile) throws Exception {
        ImageDto imageDto = new ImageDto();
        imageDto.setFilename(multipartFile.getOriginalFilename());
        imageDto.setSource(multipartFile.getBytes());
        imageDto.setType(multipartFile.getContentType());
        imageDto.setObjects(visionService.detectObjects(multipartFile.getBytes()));

        return databaseService.insertImage(imageDto);
    }

    private ImageDto convertImageEntityToDto(ImageEntity entity) {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(entity.getId());
        imageDto.setFilename(entity.getFilename());
        imageDto.setType(entity.getType());
        imageDto.setSource(entity.getSource());
        if (entity.getObjects() != null) {
            imageDto.setObjects(Arrays.stream(entity.getObjects()).toList());
        }

        return imageDto;
    }
}
