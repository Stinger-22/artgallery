package com.storagems.storage;

import com.storagems.storage.exception.InvalidFileExtensionException;
import com.storagems.storage.exception.StorageException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageValidator {
    private final static long MAXIMUM_FILE_SIZE_ALLOWED = 5242880;

    public void validateUploadedFile(MultipartFile file) {
        validateExtension(file);
        validateFileSize(file);
    }

    private void validateExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename().toLowerCase());
        if (!"png".equals(extension) && !"jpeg".equals(extension) && !"jpg".equals(extension)) {
            throw new InvalidFileExtensionException("Only jpg/jpeg and png files are accepted");
        }
    }

    private void validateFileSize(MultipartFile file){
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }
        if (file.getSize() >= MAXIMUM_FILE_SIZE_ALLOWED) {
            throw new StorageException("File size cannot be greater than 5 Mb");
        }
    }
}
