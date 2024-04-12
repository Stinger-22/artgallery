package com.imagems.like;

import com.imagems.image.Image;
import org.springframework.data.repository.CrudRepository;

public interface LikeRepository extends CrudRepository<Like, LikeID> {
    Integer deleteAllByLikeID_ImageId(Image likeID_imageId);
}
