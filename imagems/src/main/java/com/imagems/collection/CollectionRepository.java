package com.imagems.collection;

import org.springframework.data.repository.CrudRepository;

public interface CollectionRepository extends CrudRepository<Collection, Long> {
    Collection findCollectionByCollectionId(Long id);
    Integer deleteByCollectionId(Long id);
    Iterable<Collection> findCollectionByUserId(Long userId);
}
