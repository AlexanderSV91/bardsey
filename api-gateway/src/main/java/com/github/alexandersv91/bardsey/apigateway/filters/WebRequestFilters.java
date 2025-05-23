package com.github.alexandersv91.bardsey.apigateway.filters;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class WebRequestFilters implements WebFilter {

    @Override
    public Mono<Void> filter(@NotNull ServerWebExchange serverWebExchange,
                             @NotNull WebFilterChain webFilterChain) {
        log.info("Path: {}", serverWebExchange.getRequest().getPath());
        log.info("Request: {} {}", serverWebExchange.getRequest().getMethod(), serverWebExchange.getRequest().getURI());
        return webFilterChain.filter(serverWebExchange).doOnSuccess(_ -> log.info("Response Status: {}", serverWebExchange.getResponse().getStatusCode()));
    }
}
