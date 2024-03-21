package com.artgallery.service;

import com.artgallery.db.entity.Tag;
import com.artgallery.db.repository.TagRepository;
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

    public Optional<Tag> getTagById(Long id) {
        return Optional.ofNullable(tagRepository.findByTagId(id));
    }
    public Optional<Tag> getTagByName(String name) {
        return Optional.ofNullable(tagRepository.findByTagName(name));
    }

    public Iterable<Tag> searchTagsByName(String name) {
        return tagRepository.findTagsByTagNameContains(name);
    }

    public Tag createTag(Tag newTag) {
        return tagRepository.save(newTag);
    }
}
