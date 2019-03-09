package org.dbiagi.marketplace.entity.constraints;

import org.dbiagi.marketplace.normalizer.UsernameNormalizer;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
 * @TODO Make jpa custom validator work with autowired dependencies.
 */
public class UsernameValidator implements ConstraintValidator<Username, String> {

    private UsernameNormalizer usernameNormalizer;

    public UsernameValidator(UsernameNormalizer usernameNormalizer) {
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
