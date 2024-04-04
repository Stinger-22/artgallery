package com.artgallery.controller;

import com.artgallery.db.entity.Image;
import com.artgallery.db.entity.User;
import com.artgallery.service.ImageService;
import com.artgallery.service.UserService;
import com.artgallery.storage.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

@RestController
@RequestMapping({"/api/v1/user", "/api/user"})
public class UserController {
    private final UserService userService;
    private final ImageService imageService;
    private final StorageService storageService;

    public UserController(UserService userService, ImageService imageService, StorageService storageService) {
        this.userService = userService;
        this.imageService = imageService;
        this.storageService = storageService;
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
    public Iterable<Image> getImages(@PathVariable Long userId) {
        return userService.getImages(userId);
    }

//    @PostMapping("/image")
//    public Image postImage(@RequestBody Image newImage, @RequestParam("file") MultipartFile fileImage) {
//        Image createdImage = imageService.createImage(newImage);
//        storageService.store(fileImage);
//        return createdImage;
//    }

    @PostMapping("/image")
    public ResponseEntity<?> postImage(@RequestPart("image") Image image, @RequestParam("file") MultipartFile fileImage) {
        storageService.store(fileImage);
        System.out.println(image);
        return ResponseEntity.status(HttpStatus.OK).body("File successfully uploaded");
    }

    @DeleteMapping("/image/{name}")
    @Transactional
    public Image deleteImage(@PathVariable Long id) {
        return userService.deleteImage(id);
    }
}
