package com.artgallery.controller;

import com.artgallery.db.entity.Tag;
import com.artgallery.service.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping()
    public Iterable<Tag> getTags(@RequestParam(required = false) String name) {
        if (name == null) {
            return tagService.getTags();
        }
        else {
            return tagService.searchTagsByName(name);
        }
    }

    @GetMapping("/{id}")
    public Optional<Tag> getTagById(@PathVariable Long id) {
        return tagService.getTagById(id);
    }

    @GetMapping("/{name}")
    public Optional<Tag> getTagsByName(@PathVariable String name) {
        return tagService.getTagByName(name);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Tag createTag(@RequestBody Tag newTag) {
        return tagService.createTag(newTag);
    }
}
