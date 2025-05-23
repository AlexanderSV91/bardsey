package com.github.alexandersv91.bardsey.urlshortenerservice.exceptions;

public class UrlGenerationException extends RuntimeException {

    public UrlGenerationException() {
    }

    public UrlGenerationException(String message) {
        super(message);
    }

    public UrlGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UrlGenerationException(Throwable cause) {
        super(cause);
    }

}
