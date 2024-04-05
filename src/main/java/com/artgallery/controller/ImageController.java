package com.artgallery.controller;

import com.artgallery.db.entity.Image;
import com.artgallery.dto.LikeDTO;
import com.artgallery.service.ImageService;
import com.artgallery.util.ImageResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping({"/api/v1/image", "/api/image"})
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{imageId}")
    public Optional<Image> getImageById(@PathVariable Long imageId) {
        return imageService.getImageById(imageId);
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

    @PostMapping("/like")
    public void likeImage(@RequestBody LikeDTO likeDTO) {
        //TODO Get userID from authenticated (Spring Security)
        imageService.likeImage(likeDTO);
    }

    @DeleteMapping("/like")
    public void unlikeImage(@RequestBody LikeDTO likeDTO) {
        //TODO Get userID from authenticated (Spring Security)
        imageService.unlikeImage(likeDTO);
    }
}
