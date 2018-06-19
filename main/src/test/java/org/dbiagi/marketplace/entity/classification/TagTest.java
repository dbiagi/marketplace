package org.dbiagi.marketplace.entity.classification;

import org.dbiagi.marketplace.entity.EntityTest;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.*;

@org.junit.jupiter.api.Tag("model")
public class TagTest extends EntityTest {
    private Tag getValidTag() {
        Tag tag = new Tag();
        String name = faker.lorem().sentence(1);
        tag.setName(name);
        tag.setSlug(name.toLowerCase().replace(' ', '_'));
        tag.setContext(new Context());

        return tag;
    }

    private Tag getInvalidTag() {
        return new Tag();
    }

    @Test
    public void given_ValidTag_When_Validating_Then_ViolationArrayShouldBeEmpty() {
        Set<ConstraintViolation<Tag>> violations = validator.validate(getValidTag());

        assertTrue(violations.isEmpty());
    }

    @Test
    public void given_InvalidTag_When_Validating_Then_ViolationArrayShouldNotBeEmpty() {
        Set<ConstraintViolation<Tag>> violations = validator.validate(getInvalidTag());

        assertTrue(!violations.isEmpty());
    }
}
