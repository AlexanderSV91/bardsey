package com.github.alexandersv91.bardsey.urlshortenerservice.exceptions;

public class ExistsShortenerUrlException extends RuntimeException {

    public ExistsShortenerUrlException() {
    }

    public ExistsShortenerUrlException(String message) {
        super(message);
    }

    public ExistsShortenerUrlException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistsShortenerUrlException(Throwable cause) {
        super(cause);
    }

}
