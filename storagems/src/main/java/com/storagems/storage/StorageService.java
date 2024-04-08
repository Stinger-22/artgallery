package com.storagems.storage;

import com.storagems.storage.util.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String store(MultipartFile file);
    ImageResponse retrieve(String filename);
    boolean delete(String filename);
}
