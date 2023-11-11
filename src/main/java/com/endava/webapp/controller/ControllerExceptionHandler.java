package com.endava.webapp.controller;

import com.endava.webapp.dto.ErrorMessage;
import com.endava.webapp.dto.ValidationExceptionResponse;
import com.endava.webapp.exception.DepartmentNotFoundException;
import com.endava.webapp.exception.EmailAlreadyExistsException;
import com.endava.webapp.exception.PhoneNumberAlreadyExistsException;
import com.endava.webapp.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {EmailAlreadyExistsException.class})
    public ResponseEntity<ErrorMessage> emailAlreadyExistsException(EmailAlreadyExistsException ex,
                                                                    HttpServletRequest request) {
        ErrorMessage error = getErrorMessage(ex.getMessage(), request);
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    @ExceptionHandler(value = {PhoneNumberAlreadyExistsException.class})
    public ResponseEntity<ErrorMessage> phoneNumberAlreadyExistsException(PhoneNumberAlreadyExistsException ex,
                                                                          HttpServletRequest request) {
        ErrorMessage error = getErrorMessage(ex.getMessage(), request);
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException ex,
                                                              HttpServletRequest request) {
        ErrorMessage error = getErrorMessage(ex.getMessage(), request);
        return ResponseEntity.status(NOT_FOUND).body(error);
    }

    @ExceptionHandler(value = {DepartmentNotFoundException.class})
    public ResponseEntity<ErrorMessage> departmentNotFoundException(DepartmentNotFoundException ex,
                                                                    HttpServletRequest request) {
        ErrorMessage error = getErrorMessage(ex.getMessage(), request);
        return ResponseEntity.status(NOT_FOUND).body(error);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<List<ValidationExceptionResponse>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        List<ValidationExceptionResponse> errors = ex.getBindingResult()
                .getAllErrors().stream()
                .map(error -> new ValidationExceptionResponse(
                        ((FieldError) error).getField(),
                        error.getDefaultMessage()))
                .toList();
        return ResponseEntity.status(BAD_REQUEST).body(errors);
    }

    private static ErrorMessage getErrorMessage(String ex, HttpServletRequest request) {
        return ErrorMessage.builder()
                .title(ex)
                .details(request.getRequestURI())
                .build();
    }
}
