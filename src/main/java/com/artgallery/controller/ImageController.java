package com.artgallery.controller;

import com.artgallery.db.entity.Image;
import com.artgallery.service.ImageService;
import com.artgallery.util.ImageResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/v1/image", "/api/image"})
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{imageId}/view")
    public ResponseEntity<InputStreamResource> getImageViewable(@PathVariable Long imageId) {
        ImageResponse imageResponse = imageService.getImageViewable(imageId);
        return ResponseEntity.ok()
                .contentType(imageResponse.getContentType())
                .body(imageResponse.getInputStreamResource());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public Image deleteImage(@PathVariable Long id) {
        return imageService.deleteImage(id);
    }
}
