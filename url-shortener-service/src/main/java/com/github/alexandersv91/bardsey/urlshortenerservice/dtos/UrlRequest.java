package com.github.alexandersv91.bardsey.urlshortenerservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record UrlRequest(
        @JsonProperty @NotBlank(message = "Missing field: url") String originalUrl) {
}
