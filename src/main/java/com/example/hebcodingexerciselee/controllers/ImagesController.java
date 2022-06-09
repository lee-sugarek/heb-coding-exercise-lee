package com.example.hebcodingexerciselee.controllers;

import com.example.hebcodingexerciselee.dtos.ImageDto;
import com.example.hebcodingexerciselee.services.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
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
    public List<ImageDto> getImages() {
        return imagesService.getImages();
    }

    @GetMapping(params = "objects")
    public List<ImageDto> getImagesByObjects(@RequestParam(required = false) String objects) {
        return imagesService.getImagesByObjects(objects);
    }

    @GetMapping(value = "/{imageId}")
    public ImageDto getImagesById(@PathVariable final Integer imageId) {
        return imagesService.getImagesById(imageId);
    }

    @PostMapping
    public Integer postImages(@RequestParam (value= "file" ) MultipartFile multipartFile) throws Exception {
        return imagesService.postImages(multipartFile);
    }
}