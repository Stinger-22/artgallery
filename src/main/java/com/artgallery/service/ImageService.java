package com.artgallery.service;

import com.artgallery.db.entity.Image;
import com.artgallery.db.entity.Like;
import com.artgallery.db.repository.ImageRepository;
import com.artgallery.db.repository.LikeRepository;
import com.artgallery.dto.ImageDTO;
import com.artgallery.dto.LikeDTO;
import com.artgallery.storage.StorageService;
import com.artgallery.util.DTOMapper;
import com.artgallery.util.ImageResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final LikeRepository likeRepository;
    private final StorageService storageService;
    private final DTOMapper mapper;
    public ImageService(ImageRepository imageRepository, LikeRepository likeRepository, StorageService storageService, DTOMapper mapper) {
        this.imageRepository = imageRepository;
        this.likeRepository = likeRepository;
        this.storageService = storageService;
        this.mapper = mapper;
    }

    public Image createImage(ImageDTO imageDTO, MultipartFile fileImage) {
        String filename = storageService.store(fileImage);
        Image image = mapper.toEntity(imageDTO);
        image.setFilename(filename);
        imageRepository.save(image);
        return imageRepository.save(image);
    }

    public ImageResponse getImageViewable(Long imageId) {
        ImageResponse imageResponse = new ImageResponse();
        Optional<Image> image = getImageById(imageId);
        if (image.isEmpty()) {
            throw new RuntimeException("Image which you try to view doesn't exist");
        }
        imageResponse.setInputStreamResource(new InputStreamResource(storageService.retrieve(image.get().getFilename())));
        //TODO Maybe save image type in database
        String filename = image.get().getFilename();
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

    public Optional<Image> getImageById(Long id) {
        return Optional.ofNullable(imageRepository.getImageByImageId(id));
    }

    public void likeImage(LikeDTO likeDTO) {
        Like like = mapper.toEntity(likeDTO);
        likeRepository.save(like);
    }

    public void unlikeImage(LikeDTO likeDTO) {
        Like like = mapper.toEntity(likeDTO);
        likeRepository.delete(like);
    }
}
