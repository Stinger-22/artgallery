package com.imagems.client;

import com.imagems.FeignClientConfiguration;
import com.imagems.external.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE", url = "${user-service.url}", configuration = FeignClientConfiguration.class)
public interface UserClient {
    @GetMapping("/api/user/{id}")
    User getUser(@PathVariable("id") Long id);
}
