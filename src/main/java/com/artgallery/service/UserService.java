package com.artgallery.service;

import com.artgallery.db.entity.User;
import com.artgallery.db.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findUserByUserId(id));
    }
}
