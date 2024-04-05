package com.artgallery.controller;

import com.artgallery.db.entity.Image;
import com.artgallery.db.entity.User;
import com.artgallery.dto.ImageDTO;
import com.artgallery.service.CollectionService;
import com.artgallery.service.ImageService;
import com.artgallery.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping({"/api/v1/user", "/api/user"})
public class UserController {
    private final UserService userService;
    private final ImageService imageService;
    private final CollectionService collectionService;
    public UserController(UserService userService, ImageService imageService, CollectionService collectionService) {
        this.userService = userService;
        this.imageService = imageService;
        this.collectionService = collectionService;
    }

    @GetMapping()
    public Iterable<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public User createUser(@RequestBody User newUser) {
        return userService.createUser(newUser);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public long deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("{userId}/image")
    public Iterable<Image> getUserImages(@PathVariable Long userId) {
        return userService.getUserImages(userId);
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postImage(@RequestPart("image") ImageDTO imageDTO, @RequestParam("file") MultipartFile fileImage) {
        //TODO Return appropriate status when file is not uploaded
        imageService.createImage(imageDTO, fileImage);
        return ResponseEntity.status(HttpStatus.OK).body("File successfully uploaded");
    }

    @PostMapping(value = "{userId}/collection")
    public ResponseEntity<?> createCollection(@PathVariable Long userId, @RequestParam String title) {
        collectionService.createCollection(userId, title);
        return ResponseEntity.status(HttpStatus.OK).body("Collection created");
    }

    @PatchMapping(value = "{userId}/collection/add")
    public ResponseEntity<?> addImageToCollection(@PathVariable Long userId, @RequestParam Long collectionId, @RequestParam Long imageId) {
        collectionService.addImageToCollection(collectionId, imageId);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @DeleteMapping(value = "{userId}/collection/remove")
    public ResponseEntity<?> removeImageFromCollection(@PathVariable Long userId, @RequestParam Long collectionId, @RequestParam Long imageId) {
        collectionService.removeImageFromCollection(collectionId, imageId);
        return ResponseEntity.status(HttpStatus.OK).body("Collection created");
    }

    @DeleteMapping(value = "{userId}/collection")
    public ResponseEntity<?> deleteCollection(@PathVariable Long userId, @RequestParam Long collectionId) {
        collectionService.deleteCollection(collectionId);
        return ResponseEntity.status(HttpStatus.OK).body("Collection deleted");
    }
}
