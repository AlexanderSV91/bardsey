package com.github.alexandersv91.bardsey.urlshortenerservice.errors;

import com.github.alexandersv91.bardsey.urlshortenerservice.exceptions.ExistsShortenerUrlException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@RestControllerAdvice
public class ExceptionHandlerRest extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ExistsShortenerUrlException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public final ResponseEntity<ApiError> resourceAlreadyExistsHandler(ExistsShortenerUrlException ex,
                                                                       WebRequest request) {
        final ApiError apiError = new ApiError(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST.name(),
                ex.getMessage()
        );
        return new ResponseEntity<>(
                apiError,
                HttpStatus.CONFLICT
        );
    }

    @ResponseBody
    @ExceptionHandler({SQLException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> resourceDuplicateHandler(SQLException ex,
                                                             WebRequest request) {
        ApiError apiError = new ApiError(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST.name(),
                ex.getMessage()
        );
        return new ResponseEntity<>(
                apiError,
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex,
                                            WebRequest request) {
        ApiError apiError = new ApiError(
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                ex.getLocalizedMessage(),
                "Internal server error occurred"
        );
        return new ResponseEntity<>(
                apiError,
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
