package com.example.tweet.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TweetNoPresenteException extends RuntimeException {
    private static final String MESSAGE ="Tweet Not present";

    public TweetNoPresenteException() {
        super(MESSAGE);
    }
}
