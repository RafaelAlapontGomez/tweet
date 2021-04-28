package com.example.tweet.controllers;

import com.example.tweet.controllers.exceptions.DataNotFoundException;
import com.example.tweet.dtos.HttpMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Function;

@ControllerAdvice
public class ControllerErrorAdvice {

    private static final String GENERIC_ERROR_MESSAGE = "Something was wrong";

    private final Function<Exception, String> extractMessage = exception -> Optional.ofNullable(exception)
        .map(Exception::getMessage)
        .orElse(GENERIC_ERROR_MESSAGE);

    @ExceptionHandler(DataNotFoundException.class)
    public final ResponseEntity<HttpMessageDto> dataNotFoundException(Exception ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<HttpMessageDto> runtimeException(Exception ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex);
    }

    private ResponseEntity<HttpMessageDto> buildResponse(final HttpStatus status, final Exception exception) {
        return ResponseEntity.status(status)
            .body(HttpMessageDto.builder()
                .timestamp(Timestamp.from(Instant.now()))
                .message(extractMessage.apply(exception))
                .status(status)
                .errors(null)
                .build());
    }
}


