package org.dbiagi.marketplace.exception;

import org.dbiagi.marketplace.validation.ValidationError;

import java.util.List;

public class EntityValidationException extends Exception {
    private final List<ValidationError> errors;

    public EntityValidationException(List<ValidationError> errors) {
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
