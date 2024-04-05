package com.artgallery.controller;

import com.artgallery.db.entity.Image;
import com.artgallery.db.entity.User;
import com.artgallery.dto.ImageDTO;
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
    public UserController(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
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
        imageService.createImage(imageDTO, fileImage);
        return ResponseEntity.status(HttpStatus.OK).body("File successfully uploaded");
    }
}
