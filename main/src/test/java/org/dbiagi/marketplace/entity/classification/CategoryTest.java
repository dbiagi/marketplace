package org.dbiagi.marketplace.entity.classification;

import org.dbiagi.marketplace.entity.EntityTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.assertTrue;

@Tag("model")
class CategoryTest extends EntityTest {

    private Category getValidCategory() {
        Category category = new Category();
        category.setContext(new Context());

        String name = faker.lorem().sentence(1);
        category.setName(name);
        category.setSlug(name.toLowerCase().replace(' ', '_'));

        return category;
    }

    private Category getInvalidCategory() {
        return new Category();
    }

    @Test
    void given_ValidCategory_When_Validating_Then_ViolationArrayShouldBeEmpty() {
        Set<ConstraintViolation<Category>> violations = validator.validate(getValidCategory());

        assertTrue(violations.isEmpty());
    }

    @Test
    void given_InvalidCategory_When_Validating_Then_ViolationArrayShouldNotBeEmpty() {
        Set<ConstraintViolation<Category>> violations = validator.validate(getInvalidCategory());

        assertTrue(!violations.isEmpty());
    }


}
