package com.artgallery.service;

import com.artgallery.db.entity.Image;
import com.artgallery.db.entity.User;
import com.artgallery.db.repository.ImageRepository;
import com.artgallery.db.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public UserService(UserRepository userRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findUserByUserId(id));
    }

    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }

    public long deleteUser(User user) {
        return userRepository.deleteByUserId(user.getUserId());
    }

    public long deleteUser(Long id) {
        User user = userRepository.findUserByUserId(id);
        if (user == null) {
            return 0;
        }
        else {
            return deleteUser(user);
        }
    }

    public Iterable<Image> getUserImages(Long userId) {
        User user = userRepository.findUserByUserId(userId);
        return imageRepository.getImagesByUser(user);
    }
}
