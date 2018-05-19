package org.dbiagi.marketplace.exception;

import org.dbiagi.marketplace.validation.ValidationError;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EntityValidationExceptionFactory<T> {

    private List<ValidationError> extractErrors(Set<ConstraintViolation<T>> violations) {
        List<ValidationError> errors = new ArrayList<>();

        violations.forEach(v -> {
            errors.add(new ValidationError("eita", v.getPropertyPath().toString(), v.getMessage()));
        });

        return errors;
    }

    public EntityValidationException create(Set<ConstraintViolation<T>> violations) {
        return new EntityValidationException(extractErrors(violations));
    }

}
