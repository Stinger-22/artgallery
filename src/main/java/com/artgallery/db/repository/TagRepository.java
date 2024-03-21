package com.artgallery.db.repository;

import com.artgallery.db.entity.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
    Tag findByTagId(Long id);
    Tag findByTagName(String name);
    Iterable<Tag> findTagsByTagNameContains(String name);
}
