package com.artgallery;

import com.artgallery.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(StorageProperties.class)
public class ArtgalleryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtgalleryApplication.class, args);
    }

}
