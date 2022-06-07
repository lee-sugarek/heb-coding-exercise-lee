package com.example.hebcodingexerciselee.controllers;

import com.google.cloud.vision.v1.*;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.protobuf.ByteString;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.util.ArrayList;
import java.util.List;

public class VisionController {
    public static void main(String... args) throws Exception {
        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests. After completing all of your requests, call
        // the "close" method on the client to safely clean up any remaining background resources.
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

            var imgFile = new ClassPathResource("stored_images/bicycle.jpg");
            byte[] data = StreamUtils.copyToByteArray(imgFile.getInputStream());
            ByteString imgBytes = ByteString.copyFrom(data);

            // Builds the image annotation request
            List<AnnotateImageRequest> requests = new ArrayList<>();
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
            AnnotateImageRequest request =
                    AnnotateImageRequest.newBuilder()
                            .addFeatures(Feature.newBuilder().setType(Type.OBJECT_LOCALIZATION))
                            .setImage(img)
                            .build();
            requests.add(request);

            // Initialize client that will be used to send requests. This client only needs to be created
            // once, and can be reused for multiple requests. After completing all of your requests, call
            // the "close" method on the client to safely clean up any remaining background resources.
            try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
                // Perform the request
                BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
                List<AnnotateImageResponse> responses = response.getResponsesList();

                // Display the results
                for (AnnotateImageResponse res : responses) {
                    for (LocalizedObjectAnnotation entity : res.getLocalizedObjectAnnotationsList()) {
                        System.out.format("Object name: %s%n", entity.getName());
                        System.out.format("Confidence: %s%n", entity.getScore());
                    }
                }
            }
        }
    }
}