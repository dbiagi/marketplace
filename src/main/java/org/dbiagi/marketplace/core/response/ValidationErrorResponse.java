package org.dbiagi.marketplace.core.response;

import org.dbiagi.marketplace.core.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {
    private List<ValidationError> errors = new ArrayList<>();

    public void addValidationError(ValidationError validationError) {
        errors.add(validationError);
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
