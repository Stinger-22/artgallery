package com.artgallery.service;

import com.artgallery.db.entity.Image;
import com.artgallery.db.repository.ImageRepository;
import com.artgallery.dto.ImageDTO;
import com.artgallery.storage.StorageService;
import com.artgallery.util.DTOMapper;
import com.artgallery.util.ImageResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final StorageService storageService;
    private final DTOMapper mapper;
    public ImageService(ImageRepository imageRepository, StorageService storageService, DTOMapper mapper) {
        this.imageRepository = imageRepository;
        this.storageService = storageService;
        this.mapper = mapper;
    }

    public Image createImage(ImageDTO imageDTO, MultipartFile fileImage) {
        //TODO Return appropriate status when file is not uploaded
        String filename = storageService.store(fileImage);
        Image image = mapper.toEntity(imageDTO);
        image.setFilename(filename);
        imageRepository.save(image);
        return imageRepository.save(image);
    }

    public ImageResponse getImageViewable(Long imageId) {
        ImageResponse imageResponse = new ImageResponse();
        Image image = imageRepository.getImageByImageId(imageId);
        imageResponse.setInputStreamResource(new InputStreamResource(storageService.retrieve(image.getFilename())));
        //TODO Maybe save image type in database
        String filename = image.getFilename();
        String extension = filename.substring(filename.length() - 3);
        System.out.println(extension);
        if (extension.equals("jpg")) {
            imageResponse.setContentType(MediaType.IMAGE_JPEG);
        }
        else {
            imageResponse.setContentType(MediaType.IMAGE_PNG);
        }
        return imageResponse;
    }

    public Image deleteImage(Long id) {
        return imageRepository.deleteImageByImageId(id);
    }
}
