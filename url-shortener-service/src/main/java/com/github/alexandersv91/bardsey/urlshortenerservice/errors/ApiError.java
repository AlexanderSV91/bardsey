package com.github.alexandersv91.bardsey.urlshortenerservice.errors;

public record ApiError(String status, String message, String error) {
}
