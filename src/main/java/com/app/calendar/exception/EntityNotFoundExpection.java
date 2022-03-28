package com.app.calendar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundExpection  extends RuntimeException {
    public EntityNotFoundExpection(String message) {
        super(message);
    }
}
