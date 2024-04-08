package com.imagems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ImagemsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImagemsApplication.class, args);
    }

}
