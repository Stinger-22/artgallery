package com.storagems.storage;

import com.storagems.storage.util.ImageResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/api/v1/storage", "/api/storage"})
public class StorageController {
    private final StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping()
    public ResponseEntity<InputStreamResource> getViewable(@RequestParam String filename) {
        ImageResponse imageResponse = storageService.retrieve(filename);
        return ResponseEntity.ok()
                .contentType(imageResponse.getContentType())
                .body(imageResponse.getInputStreamResource());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> store(@RequestPart("image") MultipartFile image) {
        //TODO Return appropriate status when file is not uploaded
        String filename = storageService.store(image);
        return ResponseEntity.ok(filename);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam String filename) {
        boolean deleted = storageService.delete(filename);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("File deleted successfully", HttpStatus.OK);
    }
}
