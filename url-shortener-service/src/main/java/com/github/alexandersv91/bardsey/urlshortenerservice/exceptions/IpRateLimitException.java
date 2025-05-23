package com.github.alexandersv91.bardsey.urlshortenerservice.exceptions;

public class IpRateLimitException extends RuntimeException {

    public IpRateLimitException() {
    }

    public IpRateLimitException(String message) {
        super(message);
    }

    public IpRateLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public IpRateLimitException(Throwable cause) {
        super(cause);
    }

}
