package com.artgallery.service;

import com.artgallery.db.entity.User;
import com.artgallery.db.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
