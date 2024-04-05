package com.artgallery.util;

import com.artgallery.db.entity.Image;
import com.artgallery.db.entity.Tag;
import com.artgallery.db.entity.User;
import com.artgallery.dto.ImageDTO;
import com.artgallery.service.UserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class DTOMapper {
    private final UserService userService;

    public DTOMapper(UserService userService) {
        this.userService = userService;
    }

    public Image toEntity(ImageDTO imageDTO) {
        //TODO retrieve userID from currently authenticated user (Spring Security)
        Optional<User> user = userService.getUserById(imageDTO.userId());
        if (user.isEmpty()) {
            throw new RuntimeException("User which post this image doesn't exist");
        }
        Image image = new Image(user.get(), imageDTO.title(), imageDTO.description());
        image.setTags(new ArrayList<>());
        for (String stringTag : imageDTO.tags()) {
            Tag tag = new Tag(stringTag);
            image.getTags().add(tag);
        }
        return image;
    }
}
