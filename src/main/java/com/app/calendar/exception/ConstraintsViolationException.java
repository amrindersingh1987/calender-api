package com.app.calendar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConstraintsViolationException extends RuntimeException {
    public ConstraintsViolationException(String message) {
        super(message);
    }
}

