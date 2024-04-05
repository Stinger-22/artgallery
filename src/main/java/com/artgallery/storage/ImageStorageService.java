package com.artgallery.storage;

import com.artgallery.storage.exception.StorageException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.UUID;

@Service
public class ImageStorageService implements StorageService {

    private final Path rootLocation;
    private final ImageValidator validator;

    @Autowired
    public ImageStorageService(StorageProperties properties, ImageValidator validator) {
        if (properties.getLocation().trim().isEmpty()){
            throw new StorageException("File upload location can not be Empty.");
        }
        this.rootLocation = Paths.get(properties.getLocation());
        this.validator = validator;
    }

    public String store(MultipartFile file) {
        try {
            validator.validateUploadedFile(file);
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String filename = UUID.randomUUID() + "_" + Calendar.getInstance().getTimeInMillis() + "." + extension;
            File fileToStore = new File(rootLocation + "/" + filename);
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, fileToStore.toPath());
            }
            return filename;
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public InputStream retrieve(String filename) {
        File imageFile = new File(rootLocation + "/" + filename);
        try {
            return new FileInputStream(imageFile);
        } catch (FileNotFoundException e) {
            throw new com.artgallery.storage.exception.FileNotFoundException("File with filename which is retrieved from database does not exist", e);
        }
    }
}