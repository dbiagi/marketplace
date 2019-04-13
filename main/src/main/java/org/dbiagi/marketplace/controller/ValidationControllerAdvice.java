package org.dbiagi.marketplace.controller;

import org.dbiagi.marketplace.exception.EntityConstraintValidationException;
import org.dbiagi.marketplace.response.ConstraintViolationResponse;
import org.dbiagi.marketplace.validation.ValidationError;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler({RepositoryConstraintViolationException.class})
    public ResponseEntity<Object> handleRestRepositoryValidationException(RepositoryConstraintViolationException ex, WebRequest request) {
        List<ValidationError> errors = ex.getErrors().getFieldErrors().stream()
            .map(e -> new ValidationError(e.getCode(), e.getField(), e.getDefaultMessage()))
            .collect(Collectors.toList());

        return new ResponseEntity<>(new ConstraintViolationResponse(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EntityConstraintValidationException.class})
    public ResponseEntity<Object> handleValidationException(EntityConstraintValidationException ex, WebRequest request) {
        return new ResponseEntity<>(new ConstraintViolationResponse(ex.getErrors()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
