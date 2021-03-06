package org.dbiagi.marketplace.entity.classification;

import org.dbiagi.marketplace.entity.EntityTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.assertTrue;

@Tag("model")
class ContextTest extends EntityTest {
    private Context getValidContext() {
        return new Context("default");
    }

    private Context getInvalidContext() {
        return new Context();
    }

    @Test
    void given_ValidContext_When_Validating_Then_ViolationArrayShouldBeEmpty() {
        Set<ConstraintViolation<Context>> violations = validator.validate(getValidContext());

        assertTrue(violations.isEmpty());
    }

    @Test
    void given_InvalidContext_When_Validating_Then_ViolationArrayShouldNotBeEmpty() {
        Set<ConstraintViolation<Context>> violations = validator.validate(getInvalidContext());

        assertTrue(!violations.isEmpty());
    }
}
