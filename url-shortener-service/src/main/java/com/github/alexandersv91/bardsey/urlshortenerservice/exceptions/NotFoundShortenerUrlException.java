package com.github.alexandersv91.bardsey.urlshortenerservice.exceptions;

public class NotFoundShortenerUrlException extends RuntimeException {

    public NotFoundShortenerUrlException() {
    }

    public NotFoundShortenerUrlException(String message) {
        super(message);
    }

    public NotFoundShortenerUrlException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundShortenerUrlException(Throwable cause) {
        super(cause);
    }

}
