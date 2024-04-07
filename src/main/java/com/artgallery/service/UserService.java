package com.artgallery.service;

import com.artgallery.db.entity.Image;
import com.artgallery.db.entity.User;
import com.artgallery.db.repository.ImageRepository;
import com.artgallery.db.repository.UserRepository;
import com.artgallery.dto.UserDTO;
import com.artgallery.util.DTOMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final DTOMapper mapper;

    public UserService(UserRepository userRepository, ImageRepository imageRepository, DTOMapper mapper) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.mapper = mapper;
    }

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findUserByUserId(id));
    }

    public User createUser(UserDTO newUser) {
        return userRepository.save(mapper.toEntity(newUser));
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
