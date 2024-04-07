package com.artgallery.dto;


import java.util.Date;

public record UserDTO(
        String nickname,
        Date birthDate,
        String country,
        String about
) {
}
