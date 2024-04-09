package com.imagems.client;

import com.imagems.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "STORAGE-SERVICE", configuration = FeignClientConfiguration.class)
public interface StorageClient {
    @PostMapping(value = "/api/storage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> store(@RequestPart("image") MultipartFile image);

    @DeleteMapping("/api/storage")
    ResponseEntity<String> delete(@RequestParam String filename);
}
