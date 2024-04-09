package com.imagems.mapper;

import com.imagems.dto.ImageWithUserDTO;
import com.imagems.external.User;
import com.imagems.image.Image;

public class UserMapper {
    public static ImageWithUserDTO mapToImageWithUserDTO(Image image, User user) {
        return new ImageWithUserDTO(image, user);
    }
}
