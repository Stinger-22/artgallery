package com.artgallery.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface StorageService {
    String store(MultipartFile file);
    InputStream retrieve(String filename);
}
