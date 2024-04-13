package com.imagems.mapper;

import com.imagems.dto.ImageWithUserDTO;
import com.imagems.external.User;
import com.imagems.image.Image;

public class Mapper {
    public static ImageWithUserDTO mapToImageWithUserDTO(Image image, User user) {
        ImageWithUserDTO dto = new ImageWithUserDTO();
        dto.setUser(user);
        dto.setImageId(image.getImageId());
        dto.setTitle(image.getDescription());
        dto.setDescription(image.getDescription());
        dto.setLikes(image.getLikes());
        dto.setTags(image.getTags());
        dto.setFilename(image.getFilename());
        dto.setCreationDate(image.getCreationDate());
        return dto;
    }
}
