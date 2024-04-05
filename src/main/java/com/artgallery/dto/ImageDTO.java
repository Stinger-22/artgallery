package com.artgallery.dto;

import java.util.List;

public record ImageDTO(
        Long userId,
        List<String> tags,
        String title,
        String description
) {
}
