package com.imagems.image;

import com.imagems.dto.ImageWithUserDTO;
import com.imagems.external.User;
import com.imagems.like.LikeID;
import com.imagems.tag.Tag;
import com.imagems.tag.TagRepository;
import com.imagems.like.Like;
import com.imagems.like.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;
    private final LikeRepository likeRepository;
    public ImageService(ImageRepository imageRepository, TagRepository tagRepository, LikeRepository likeRepository) {
        this.imageRepository = imageRepository;
        this.tagRepository = tagRepository;
        this.likeRepository = likeRepository;
    }

    public Image createImage(ImageDTO imageDTO, MultipartFile fileImage) {
//        String filename = storageService.store(fileImage);
        Image image = toEntity(imageDTO);
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
        return imageRepository.save(image);
    }

//    public ImageResponse getImageViewable(Long imageId) {
//        Optional<Image> image = getImageById(imageId);
//        if (image.isEmpty()) {
//            throw new RuntimeException("Image which you try to view doesn't exist");
//        }
//        return storageService.retrieve(image.getFilename);
//    }

    public Integer deleteImage(Long id) {
        return imageRepository.deleteImageByImageId(id);
    }

    public Optional<ImageWithUserDTO> getImageWithUserDTO(Long id) {
        Image image = imageRepository.getImageByImageId(id);
        if (image == null) {
            return Optional.empty();
        }
        RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject("http://localhost:8081/api/user/" + image.getUserId(), User.class);
        return Optional.of(new ImageWithUserDTO(image, user));
    }

    public Optional<Image> getImage(Long id) {
        return Optional.ofNullable(imageRepository.getImageByImageId(id));
    }

    public Iterable<Image> getImages() {
        return imageRepository.findAll();
    }

    public List<Image> findImagesByTagsIn(List<String> tags) {
        return imageRepository.findImagesByTagsIn(tags);
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

    public Like likeImage(Long id, Long userId) {
        Like like = new Like(new LikeID(imageRepository.getImageByImageId(id), userId));
        return likeRepository.save(like);
    }

    public void unlikeImage(Long id, Long userId) {
        Like like = new Like(new LikeID(imageRepository.getImageByImageId(id), userId));
        likeRepository.delete(like);
    }
}
