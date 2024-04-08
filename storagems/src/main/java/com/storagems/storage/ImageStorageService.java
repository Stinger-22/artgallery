package com.storagems.storage;

import com.storagems.storage.exception.StorageException;
import com.storagems.storage.exception.FileNotFoundException;
import com.storagems.storage.util.ImageResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
            String extension = FilenameUtils.getExtension(file.getOriginalFilename().toLowerCase());
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
    public ImageResponse retrieve(String filename) {
        File imageFile = new File(rootLocation + "/" + filename);
        try {
            ImageResponse imageResponse = new ImageResponse();
            imageResponse.setContentType(filename);
            imageResponse.setInputStreamResource(new InputStreamResource(new FileInputStream(imageFile)));
            return imageResponse;
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException("File with filename which is retrieved from database does not exist", e);
        }
    }

    @Override
    public boolean delete(String filename) {
        File file = new File(rootLocation + "/" + filename);
        try {
            return Files.deleteIfExists(file.toPath());
        } catch (java.io.IOException e) {
            throw new StorageException("File was not deleted", e);
        }
    }
}