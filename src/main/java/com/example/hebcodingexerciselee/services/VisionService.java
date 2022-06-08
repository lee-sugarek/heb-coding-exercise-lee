package com.example.hebcodingexerciselee.services;

import com.google.cloud.vision.v1.*;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VisionService {

    @Autowired
    public VisionService() {}
    public List<String> detectObjects(byte[] data) throws Exception {
        List<String> detectedObjects = new ArrayList<>();
        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests. After completing all of your requests, call
        // the "close" method on the client to safely clean up any remaining background resources.
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

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
                        detectedObjects.add(entity.getName());
                    }
                }
            }
        }

        return detectedObjects;
    }
}