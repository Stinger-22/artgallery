package com.storagems.storage.util;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;

public class ImageResponse {
    private InputStreamResource inputStreamResource;
    private MediaType contentType;

    public ImageResponse() {
    }

    public InputStreamResource getInputStreamResource() {
        return inputStreamResource;
    }

    public void setInputStreamResource(InputStreamResource inputStreamResource) {
        this.inputStreamResource = inputStreamResource;
    }

    public MediaType getContentType() {
        return contentType;
    }

    public void setContentType(MediaType contentType) {
        this.contentType = contentType;
    }

    public void setContentType(String filename) {
        String extension = filename.substring(filename.length() - 3);
        if (extension.equals("png")) {
            contentType = MediaType.IMAGE_PNG;
        }
        else {
            contentType = MediaType.IMAGE_JPEG;
        }
    }
}
