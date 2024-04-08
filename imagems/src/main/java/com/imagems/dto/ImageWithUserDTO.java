package com.imagems.dto;

import com.imagems.external.User;
import com.imagems.image.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageWithUserDTO {
    private Image image;
    private User user;
}
