package com.imagems.image;

import com.imagems.client.StorageClient;
import com.imagems.client.UserClient;
import com.imagems.dto.ImageWithUserDTO;
import com.imagems.exception.NotFoundException;
import com.imagems.external.User;
import com.imagems.like.LikeID;
import com.imagems.mapper.Mapper;
import com.imagems.tag.Tag;
import com.imagems.tag.TagRepository;
import com.imagems.like.Like;
import com.imagems.like.LikeRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;
    private final LikeRepository likeRepository;
    private final UserClient userClient;
    private final StorageClient storageClient;

    public ImageService(ImageRepository imageRepository, TagRepository tagRepository, LikeRepository likeRepository, UserClient userClient, StorageClient storageClient) {
        this.imageRepository = imageRepository;
        this.tagRepository = tagRepository;
        this.likeRepository = likeRepository;
        this.userClient = userClient;
        this.storageClient = storageClient;
    }

    public Optional<Image> createImage(ImageDTO imageDTO, MultipartFile fileImage) {
        try {
            User user = userClient.getUser(imageDTO.userId());
        } catch (NotFoundException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        String filename = storageClient.store(fileImage).getBody();
        Image image = toEntity(imageDTO);
        image.setFilename(filename);
        image.setLikes(0);
        image.setTags(new HashSet<>());
        for (String stringTag : imageDTO.tags()) {
            Tag foundTag = tagRepository.findByTagName(stringTag);
            if (foundTag == null) {
                Tag tag = new Tag(stringTag);
                image.getTags().add(tag);
                tagRepository.save(tag);
            }
            else {
                image.getTags().add(foundTag);
            }
        }
        imageRepository.save(image);
        return Optional.of(imageRepository.save(image));
    }

//    public ImageResponse getImageViewable(Long imageId) {
//        Optional<Image> image = getImageById(imageId);
//        if (image.isEmpty()) {
//            throw new RuntimeException("Image which you try to view doesn't exist");
//        }
//        return storageService.retrieve(image.getFilename);
//    }

    public Integer deleteImage(Long id) {
        Image image = imageRepository.getImageByImageId(id);
        if (image == null) {
            return 0;
        }
        likeRepository.deleteAllByLikeID_ImageId(image);
        storageClient.delete(image.getFilename());
        return imageRepository.deleteImageByImageId(id);
    }

    @Cacheable(value = "imageCache", key = "#id")
    public Optional<ImageWithUserDTO> getImageWithUserDTO(Long id) {
        Image image = imageRepository.getImageByImageId(id);
        if (image == null) {
            return Optional.empty();
        }
        User user = userClient.getUser(image.getUserId());
        return Optional.of(Mapper.mapToImageWithUserDTO(image, user));
    }

    public Optional<Image> getImage(Long id) {
        return Optional.ofNullable(imageRepository.getImageByImageId(id));
    }

    public Iterable<ImageWithUserDTO> getImages() {
        List<Image> images = imageRepository.findAll();
        return images.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ImageWithUserDTO> findImagesByTagsIn(List<String> tags) {
        List<Image> images = imageRepository.findImagesByTagsIn(tags);
        return images.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ImageWithUserDTO toDTO(Image image) {
        User user = userClient.getUser(image.getUserId());
        return Mapper.mapToImageWithUserDTO(image, user);
    }

    private Image toEntity(ImageDTO imageDTO) {
        //TODO retrieve userID from currently authenticated user (Spring Security)
//        User user = userRepository.findUserByUserId(imageDTO.userId());
//        if (user == null) {
//            throw new RuntimeException("User which post this image doesn't exist");
//        }
        Image image = new Image();
        image.setUserId(imageDTO.userId());
        image.setTitle(imageDTO.title());
        image.setDescription(imageDTO.description());

        return image;
    }

    public Optional<Like> likeImage(Long id, Long userId) {
        try {
            User user = userClient.getUser(userId);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        Image image = imageRepository.getImageByImageId(id);
        if (image == null) {
            return Optional.empty();
        }
        Like like = new Like(new LikeID(image, userId));
        return Optional.of(likeRepository.save(like));
    }

    public void unlikeImage(Long id, Long userId) {
        try {
            User user = userClient.getUser(userId);
        } catch (NotFoundException e) {
            e.printStackTrace();
//            return Optional.empty();
        }
        Image image = imageRepository.getImageByImageId(id);
        if (image == null) {
            throw new NotFoundException("Image which is disliked does not exist");
        }
        Like like = new Like(new LikeID(image, userId));
        likeRepository.delete(like);
    }
}
