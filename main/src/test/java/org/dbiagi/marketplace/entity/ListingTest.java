package org.dbiagi.marketplace.entity;

import org.junit.Test;
import org.junit.jupiter.api.Tag;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.*;


@Tag("model")
public class ListingTest extends EntityTest {
    private Listing getInvalidListing() {
        return new Listing();
    }

    private Listing getValidListing() {
        Listing listing = new Listing();

        listing.setTitle(faker.lorem().sentence(1));
        listing.setSlug(faker.internet().slug());

        return listing;
    }

    @Test
    public void given_InvalidListing_When_Validating_Then_ViolationArrayShouldNotBeEmpty() {
        Listing listing = getInvalidListing();

        Set<ConstraintViolation<Listing>> violations = validator.validate(listing);

        assertTrue(!violations.isEmpty());
    }

    @Test
    public void given_ValidListing_When_Validating_Then_ViolationArrayShouldBeEmpty() {
        Listing listing = getValidListing();

        Set<ConstraintViolation<Listing>> violations = validator.validate(listing);

        assertTrue(violations.isEmpty());
    }
}
