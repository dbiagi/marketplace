package org.dbiagi.marketplace.core.controller;

import org.dbiagi.marketplace.core.response.ValidationErrorResponse;
import org.dbiagi.marketplace.core.validation.ValidationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestController
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        ValidationErrorResponse response = new ValidationErrorResponse();

        fieldErrors.forEach(error -> {
            ValidationError validationError = new ValidationError();
            validationError.setCode(error.getCode());
            validationError.setField(error.getField());
            validationError.setMessage(error.getDefaultMessage());

            response.addValidationError(validationError);
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
