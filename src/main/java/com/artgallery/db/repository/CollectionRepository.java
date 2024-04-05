package com.artgallery.db.repository;

import com.artgallery.db.entity.Collection;
import org.springframework.data.repository.CrudRepository;

public interface CollectionRepository extends CrudRepository<Collection, Long> {
    Collection findCollectionByCollectionId(Long id);
}
