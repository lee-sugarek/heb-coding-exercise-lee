package com.example.hebcodingexerciselee.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagesService {

    @Autowired
    public ImagesService() {

    }

    public String getImages() {
        return "All Images API call";
    }

    public String getImagesByObjects(List<String> objects) {

        return ("Images by objects " + objects.toString());
    }

    public String getImagesById(String imageId) {
        return ("Images By Id API call " + imageId);
    }

    public String postImages() {
        return "Post Images API call";
    }
}
