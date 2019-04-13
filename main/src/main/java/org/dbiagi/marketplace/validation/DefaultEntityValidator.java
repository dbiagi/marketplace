package org.dbiagi.marketplace.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.persistence.Entity;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.springframework.core.annotation.AnnotatedElementUtils.isAnnotated;

@Component
public class DefaultEntityValidator implements org.springframework.validation.Validator {
    private final Validator validator;

    public DefaultEntityValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return isAnnotated(clazz, Entity.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Set<ConstraintViolation<Object>> violations = validator.validate(target);

        if (violations.isEmpty()) {
            return;
        }

        violations.forEach(v -> errors.rejectValue(v.getPropertyPath().toString(), null, v.getMessage()));
    }
}
