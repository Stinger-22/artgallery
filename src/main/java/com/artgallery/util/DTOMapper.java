package com.artgallery.util;

import com.artgallery.db.LikeID;
import com.artgallery.db.entity.Image;
import com.artgallery.db.entity.Like;
import com.artgallery.db.entity.Tag;
import com.artgallery.db.entity.User;
import com.artgallery.db.repository.ImageRepository;
import com.artgallery.db.repository.UserRepository;
import com.artgallery.dto.ImageDTO;
import com.artgallery.dto.LikeDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DTOMapper {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public DTOMapper(UserRepository userRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    public Image toEntity(ImageDTO imageDTO) {
        //TODO retrieve userID from currently authenticated user (Spring Security)
        User user = userRepository.findUserByUserId(imageDTO.userId());
        if (user == null) {
            throw new RuntimeException("User which post this image doesn't exist");
        }
        Image image = new Image(user, imageDTO.title(), imageDTO.description());
        image.setTags(new ArrayList<>());
        for (String stringTag : imageDTO.tags()) {
            Tag tag = new Tag(stringTag);
            image.getTags().add(tag);
        }
        return image;
    }

    public Like toEntity(LikeDTO likeDTO) {
        //TODO retrieve userID from currently authenticated user (Spring Security)
        User user = userRepository.findUserByUserId(likeDTO.userId());
        if (user == null) {
            throw new RuntimeException("User which likes this image doesn't exist");
        }
        Image image = imageRepository.getImageByImageId(likeDTO.imageId());
        if (image == null) {
            throw new RuntimeException("Image which is liked doesn't exist");
        }
        return new Like(new LikeID(image, user));
    }
}
