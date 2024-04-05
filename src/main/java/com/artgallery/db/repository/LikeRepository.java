package com.artgallery.db.repository;

import com.artgallery.db.LikeID;
import com.artgallery.db.entity.Like;
import org.springframework.data.repository.CrudRepository;

public interface LikeRepository extends CrudRepository<Like, LikeID> {
}
