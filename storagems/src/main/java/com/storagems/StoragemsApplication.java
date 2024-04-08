package com.storagems;

import com.storagems.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class StoragemsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoragemsApplication.class, args);
    }

}
