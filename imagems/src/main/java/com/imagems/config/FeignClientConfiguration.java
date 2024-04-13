package com.imagems.config;

import com.imagems.util.RequestErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class FeignClientConfiguration {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new RequestErrorDecoder();
    }
}
