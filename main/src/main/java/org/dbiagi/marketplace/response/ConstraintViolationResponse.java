package org.dbiagi.marketplace.response;

import lombok.Data;
import org.dbiagi.marketplace.validation.ValidationError;

import java.util.Collection;

@Data
public class ConstraintViolationResponse {
    private Collection<ValidationError> errors;
    private String message = "{validation.response.message}";

    public ConstraintViolationResponse(Collection<ValidationError> errors) {
        this.errors = errors;
    }
}
