package com.github.alexandersv91.bardsey.urlshortenerservice.error;

public record ApiError(String status, String message, String error) {
}
