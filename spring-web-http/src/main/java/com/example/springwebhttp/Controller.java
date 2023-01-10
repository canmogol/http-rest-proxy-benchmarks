package com.example.springwebhttp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/")
    public String get() {
        return restTemplate.getForObject("http://192.168.68.118:9090/test.txt", String.class);
    }
}
