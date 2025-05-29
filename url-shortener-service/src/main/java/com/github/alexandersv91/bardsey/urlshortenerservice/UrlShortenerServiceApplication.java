package com.github.alexandersv91.bardsey.urlshortenerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication(
        scanBasePackages = "com.github.alexandersv91.bardsey.urlshortenerservice"
//        exclude = {
//                DataSourceAutoConfiguration.class,
//                HibernateJpaAutoConfiguration.class
//        }
)
public class UrlShortenerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerServiceApplication.class, args);
    }

}
