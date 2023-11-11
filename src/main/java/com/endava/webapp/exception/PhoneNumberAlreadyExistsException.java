package com.endava.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PhoneNumberAlreadyExistsException extends RuntimeException{
    public PhoneNumberAlreadyExistsException(String message) {
        super(message);
    }
}
