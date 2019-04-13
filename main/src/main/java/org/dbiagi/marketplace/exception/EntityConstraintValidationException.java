package org.dbiagi.marketplace.exception;

import org.dbiagi.marketplace.validation.ValidationError;

import java.util.List;

public class EntityConstraintValidationException extends Exception {
    private List<ValidationError> errors;

    public EntityConstraintValidationException(List<ValidationError> errors) {
        super("Validation exception");
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
