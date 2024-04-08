package com.imagems.tag;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Iterable<Tag> getTags() {
        return tagRepository.findAll();
    }

    public Optional<Tag> getTag(Long id) {
        return Optional.ofNullable(tagRepository.findByTagId(id));
    }

    public Iterable<Tag> searchTags(String name) {
        return tagRepository.findTagsByTagNameContains(name);
    }

    public Tag createTag(TagDTO newTag) {
        return tagRepository.save(toEntity(newTag));
    }

    public Integer deleteTag(Long id) {
        return tagRepository.deleteTagByTagId(id);
    }

    private Tag toEntity(TagDTO tagDTO) {
        return new Tag(tagDTO.tagName());
    }
}
