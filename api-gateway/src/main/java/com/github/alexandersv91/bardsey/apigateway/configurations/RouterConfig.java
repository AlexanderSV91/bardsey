package com.github.alexandersv91.bardsey.apigateway.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("url-shortener-service",
                        r -> r

                                .path("/api/v1/url/**")
                                // .filters(f -> f.filter(jwtAuthorizationFilter))
                                .uri("lb://URL-SHORTENER-SERVICE")
                )
                .build();
    }
}
