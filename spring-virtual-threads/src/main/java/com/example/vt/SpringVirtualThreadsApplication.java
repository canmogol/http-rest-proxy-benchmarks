package com.example.vt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

@SpringBootApplication
public class SpringVirtualThreadsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringVirtualThreadsApplication.class, args);
    }

    @Bean
    public TomcatProtocolHandlerCustomizer<?> tomcatProtocolHandlerCustomizer() {
        return protocolHandler -> {
            protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        };
    }

}

@RestController
class ApiController {
    private final HttpClient httpClient = HttpClient
            .newBuilder()
            .executor(Executors.newVirtualThreadPerTaskExecutor())
            .build();
    private final HttpRequest httpRequest = HttpRequest
            .newBuilder()
            .GET()
            .uri(URI.create("http://192.168.68.118:9090/test.txt"))
            .build();

    @GetMapping("/")
    public String get() throws IOException, InterruptedException {
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("Thread.currentThread().isVirtual() = " + Thread.currentThread().isVirtual());
        return httpResponse.body();
    }
}
