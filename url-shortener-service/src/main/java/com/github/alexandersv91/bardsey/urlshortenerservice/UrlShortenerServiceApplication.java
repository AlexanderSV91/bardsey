package com.github.alexandersv91.bardsey.urlshortenerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class UrlShortenerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerServiceApplication.class, args);
    }

}
