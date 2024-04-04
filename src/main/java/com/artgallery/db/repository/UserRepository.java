package com.artgallery.db.repository;

import com.artgallery.db.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUserId(Long id);
    long deleteByUserId(Long id);
}