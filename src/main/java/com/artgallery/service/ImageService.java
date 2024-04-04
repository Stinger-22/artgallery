package com.artgallery.service;

import com.artgallery.db.entity.Image;
import com.artgallery.db.repository.ImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image createImage(Image newImage) {
//        newImage.setFilename();
        return imageRepository.save(newImage);
    }
}
