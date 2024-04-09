package com.imagems.collection;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/v1/collection", "/api/collection"})
public class CollectionController {
    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Collection> getCollections(@RequestParam(required = false) Long userId) {
        if (userId == null) {
            return collectionService.getCollections();
        }
        else {
            return collectionService.getCollectionsOfUser(userId);
        }
    }

    @GetMapping("/{collectionId}")
    public ResponseEntity<Collection> getCollection(@PathVariable Long collectionId) {
        Collection collection = collectionService.getCollection(collectionId).orElse(null);
        return ResponseEntity.ofNullable(collection);
    }

    @PostMapping
    public ResponseEntity<Collection> createCollection(@RequestParam Long userId, @RequestParam String title) {
        Collection collection = collectionService.createCollection(userId, title).orElse(null);
        return ResponseEntity.ofNullable(collection);
    }

    @PutMapping("/{collectionId}")
    public ResponseEntity<?> renameCollection(@PathVariable Long collectionId, @RequestParam String title) {
        try {
            Collection collection = collectionService.renameCollection(collectionId, title);
            return ResponseEntity.status(HttpStatus.OK).body(collection);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{collectionId}/image")
    public ResponseEntity<?> addImageToCollection(@PathVariable Long collectionId, @RequestParam Long imageId) {
        try {
            Collection collection = collectionService.addImageToCollection(collectionId, imageId);
            return ResponseEntity.status(HttpStatus.OK).body(collection);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{collectionId}/image")
    public ResponseEntity<?> removeImageFromCollection(@PathVariable Long collectionId, @RequestParam Long imageId) {
        try {
            Collection collection = collectionService.removeImageFromCollection(collectionId, imageId);
            return ResponseEntity.status(HttpStatus.OK).body(collection);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{collectionId}")
    @Transactional
    public ResponseEntity<String> deleteCollection(@PathVariable Long collectionId) {
        Integer deleted = collectionService.deleteCollection(collectionId);
        if (deleted == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Collection deleted successfully", HttpStatus.OK);
    }
}
