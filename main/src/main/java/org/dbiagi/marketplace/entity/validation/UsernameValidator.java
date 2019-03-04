package org.dbiagi.marketplace.entity.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UsernameValidator implements ConstraintValidator<Username, String> {

    private org.dbiagi.marketplace.normalizer.Username usernameNormalizer;

    @Autowired
    public UsernameValidator(org.dbiagi.marketplace.normalizer.Username usernameNormalizer) {
        this.usernameNormalizer = usernameNormalizer;
    }

    @Override
    public void initialize(Username constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return value.equals(usernameNormalizer.normalize(value));
    }
}
