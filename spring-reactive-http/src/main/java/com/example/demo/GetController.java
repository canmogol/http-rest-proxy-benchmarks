package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;

@RestController
@RequestMapping(value = "/")
public class GetController {

    @GetMapping("/")
    public Flux<byte[]> get() {
        return HttpClient.create()
                .get()
                .uri("http://192.168.68.118:9090/test.txt")
                .responseContent()
                .asByteArray();
    }

}

