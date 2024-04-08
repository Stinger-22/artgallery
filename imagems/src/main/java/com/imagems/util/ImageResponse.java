package com.imagems.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;

@Getter
@Setter
@NoArgsConstructor
public class ImageResponse {
    InputStreamResource inputStreamResource;
    MediaType contentType;
}
