package com.imagems.image;

import com.imagems.dto.ImageWithUserDTO;
import com.imagems.like.Like;
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
    public ResponseEntity<?> getImage(@PathVariable Long imageId) {
        ImageWithUserDTO imageWithUserDTO = imageService.getImageWithUserDTO(imageId).orElse(null);
        if (imageWithUserDTO == null) {
            return new ResponseEntity<>("Image with this ID does not exist", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(imageWithUserDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<ImageWithUserDTO> findImagesByTagsIn(@RequestParam(required = false) List<String> tags) {
        if (tags == null) {
            return imageService.getImages();
        }
        return imageService.findImagesByTagsIn(tags);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Image> createImage(@RequestPart("image") ImageDTO imageDTO, @RequestPart("file") MultipartFile fileImage) {
        Image image = imageService.createImage(imageDTO, fileImage).orElse(null);
        return ResponseEntity.ofNullable(image);
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
    public ResponseEntity<String> likeImage(@PathVariable Long id, @RequestParam Long userId) {
        //TODO Get userID from authenticated (Spring Security)
        Like like = imageService.likeImage(id, userId).orElse(null);
        if (like == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Image liked successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}/unlike")
    public void unlikeImage(@PathVariable Long id, @RequestParam Long userId) {
        //TODO Get userID from authenticated (Spring Security)
        imageService.unlikeImage(id, userId);
    }
}
