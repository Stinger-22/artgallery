package com.imagems.image;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping({"/api/v1/image", "/api/image"})
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<Image> getImage(@PathVariable Long imageId) {
        Image image = imageService.getImage(imageId).orElse(null);
        return ResponseEntity.ofNullable(image);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Image> findImagesByTagsIn(@RequestParam(required = false) List<String> tags) {
        if (tags == null) {
            return imageService.getImages();
        }
        return imageService.findImagesByTagsIn(tags);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Image createImage(@RequestPart("image") ImageDTO imageDTO, @RequestPart("file") MultipartFile fileImage) {
        //TODO Return appropriate status when file is not uploaded
        return imageService.createImage(imageDTO, fileImage);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteImage(@PathVariable Long id) {
        Integer deleted = imageService.deleteImage(id);
        if (deleted == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/{id}/like")
    public void likeImage(@PathVariable Long id, @RequestParam Long userId) {
        //TODO Get userID from authenticated (Spring Security)
        imageService.likeImage(id, userId);
    }

    @DeleteMapping("/{id}/unlike")
    public void unlikeImage(@PathVariable Long id, @RequestParam Long userId) {
        //TODO Get userID from authenticated (Spring Security)
        imageService.unlikeImage(id, userId);
    }
}
