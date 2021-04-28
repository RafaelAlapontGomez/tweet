package com.example.tweet.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNoPresenteException extends RuntimeException {
    private static final String MESSAGE ="User Not valid";

    public UserNoPresenteException() {
        super(MESSAGE);
    }
}
