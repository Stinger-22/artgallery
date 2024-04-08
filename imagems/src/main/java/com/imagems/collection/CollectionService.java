package com.imagems.collection;

import com.imagems.image.Image;
import com.imagems.image.ImageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionService {
    private final CollectionRepository collectionRepository;
    private final ImageService imageService;

    public CollectionService(CollectionRepository collectionRepository, ImageService imageService) {
        this.collectionRepository = collectionRepository;
        this.imageService = imageService;
    }

    public Iterable<Collection> getCollections() {
        return collectionRepository.findAll();
    }

    public Optional<Collection> getCollection(Long id) {
        return Optional.ofNullable(collectionRepository.findCollectionByCollectionId(id));
    }

    public Iterable<Collection> getCollectionsOfUser(Long id) {
        return collectionRepository.findCollectionByUserId(id);
    }

    public Collection createCollection(Long userId, String title) {
        Collection collection = new Collection();
        collection.setUserId(userId);
        collection.setTitle(title);
        return collectionRepository.save(collection);
    }

    public Integer deleteCollection(Long collectionId) {
        return collectionRepository.deleteByCollectionId(collectionId);
    }

    public Collection addImageToCollection(Long collectionId, Long imageId) {
        Collection collection = collectionRepository.findCollectionByCollectionId(collectionId);
        Optional<Image> image = imageService.getImage(imageId);
        if (image.isEmpty()) {
            throw new RuntimeException("Image is not found");
        }
        List<Image> images = collection.getImages();
        images.add(image.get());
        return collectionRepository.save(collection);
    }

    public Collection removeImageFromCollection(Long collectionId, Long imageId) {
        Collection collection = collectionRepository.findCollectionByCollectionId(collectionId);
        Optional<Image> image = imageService.getImage(imageId);
        if (image.isEmpty()) {
            throw new RuntimeException("");
        }
        List<Image> images = collection.getImages();
        if (!images.contains(image.get())) {
            throw new RuntimeException("Image in collection not found");
        }
        images.remove(image.get());
        return collectionRepository.save(collection);
    }

    public Collection renameCollection(Long collectionId, String title) {
        Collection collection = collectionRepository.findCollectionByCollectionId(collectionId);
        if (collection == null) {
            throw new RuntimeException("Collection is not found");
        }
        collection.setTitle(title);
        return collectionRepository.save(collection);
    }
}
