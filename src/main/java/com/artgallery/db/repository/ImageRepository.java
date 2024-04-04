package com.artgallery.db.repository;

import com.artgallery.db.entity.Image;
import com.artgallery.db.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
    Image getImageByImageId(Long id);
    Iterable<Image> getImagesByUser(User user);
    Image deleteImageByImageId(Long id);
}
