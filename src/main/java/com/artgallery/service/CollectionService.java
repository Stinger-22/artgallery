package com.artgallery.service;

import com.artgallery.db.entity.Collection;
import com.artgallery.db.entity.Image;
import com.artgallery.db.repository.CollectionRepository;
import com.artgallery.db.repository.ImageRepository;
import com.artgallery.db.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService {
    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public CollectionService(CollectionRepository collectionRepository, UserRepository userRepository, ImageRepository imageRepository) {
        this.collectionRepository = collectionRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    public void createCollection(Long userId, String title) {
        Collection collection = new Collection();
        collection.setUser(userRepository.findUserByUserId(userId));
        collection.setTitle(title);
        collectionRepository.save(collection);
    }

    public void deleteCollection(Long collectionId) {
        collectionRepository.delete(collectionRepository.findCollectionByCollectionId(collectionId));
    }

    public void addImageToCollection(Long collectionId, Long imageId) {
        Collection collection = collectionRepository.findCollectionByCollectionId(collectionId);
        Image image = imageRepository.getImageByImageId(imageId);
        List<Image> images = collection.getImages();
        images.add(image);
        collectionRepository.save(collection);
    }

    public void removeImageFromCollection(Long collectionId, Long imageId) {
        Collection collection = collectionRepository.findCollectionByCollectionId(collectionId);
        Image image = imageRepository.getImageByImageId(imageId);
        List<Image> images = collection.getImages();
        images.remove(image);
        collectionRepository.save(collection);
    }
}
