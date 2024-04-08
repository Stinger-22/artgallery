package com.imagems.tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/v1/tag", "/api/tag"})
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Tag> getTags(@RequestParam(required = false) String name) {
        if (name == null) {
            return tagService.getTags();
        }
        else {
            return tagService.searchTags(name);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable Long id) {
        Tag tag = tagService.getTag(id).orElse(null);
        return ResponseEntity.ofNullable(tag);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Tag createTag(@RequestBody TagDTO newTag) {
        return tagService.createTag(newTag);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteTag(@PathVariable Long id) {
        Integer deleted = tagService.deleteTag(id);
        if (deleted == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Tag deleted successfully", HttpStatus.OK);
    }
}
