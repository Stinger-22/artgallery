package com.artgallery.db.repository;

import com.artgallery.db.entity.Image;
import com.artgallery.db.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImageRepository extends CrudRepository<Image, Long> {
    Image getImageByImageId(Long id);
    Iterable<Image> getImagesByUser(User user);
    Image deleteImageByImageId(Long id);

    @Query("select i from Image i left join i.tags tags where tags.tagName in ?1")
    List<Image> findImagesByTagsIn(List<String> tags);
}
