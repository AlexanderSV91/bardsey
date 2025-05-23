package com.github.alexandersv91.bardsey.apigateway.handlers;

import com.github.alexandersv91.bardsey.apigateway.exceptions.CommonHttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class GlobalErrorHandler implements ErrorWebExceptionHandler {
    private static final int DEFAULT_STATUS_CODE = 500;
    private static final String DEFAULT_MESSAGE = "Error in the gateway";

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.debug(ex.getMessage());
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        int statusCode = DEFAULT_STATUS_CODE;
        String message = DEFAULT_MESSAGE;

        if (ex instanceof CommonHttpException commonEx) {
            statusCode = commonEx.getStatusCode();
            message = commonEx.getMessage();
        }

        response.setStatusCode(HttpStatus.resolve(statusCode));

        String body = String.format("{\"status\": %d, \"message\": \"%s\"}", statusCode, message);
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(buffer));
    }
}
