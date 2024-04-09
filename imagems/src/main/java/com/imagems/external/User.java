package com.imagems.external;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long userId;

    private String nickname;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;

    private String country;

    private String about;

    private Date creationDate;
}
