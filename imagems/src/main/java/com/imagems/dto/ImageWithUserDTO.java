package com.imagems.dto;

import com.imagems.external.User;
import com.imagems.tag.Tag;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ImageWithUserDTO implements Serializable {
    private Long imageId;
    private String title;
    private String description;
    private Integer likes;
    private Set<Tag> tags;
    private String filename;
    private Date creationDate;

    private User user;
}
