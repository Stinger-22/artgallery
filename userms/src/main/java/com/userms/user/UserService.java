package com.userms.user;

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

    public Optional<User> getUser(Long id) {
        return Optional.ofNullable(userRepository.findUserByUserId(id));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User updatedUser, Long id) {
        User originUser = userRepository.findUserByUserId(id);
        if (originUser == null) {
            updatedUser.setUserId(id);
            return userRepository.save(updatedUser);
        }
        originUser.setAbout(updatedUser.getAbout());
        originUser.setCountry(updatedUser.getCountry());
        originUser.setNickname(updatedUser.getNickname());
        originUser.setBirthDate(updatedUser.getBirthDate());
        return userRepository.save(originUser);
    }

    public Integer deleteUser(Long id) {
        return userRepository.deleteByUserId(id);
    }
}
