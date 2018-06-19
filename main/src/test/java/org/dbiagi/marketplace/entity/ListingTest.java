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
        listing.setStore(new Store());

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

    @Test
    public void when_UsingLombokBuilder_Then_ShouldReturnAListing() {
        assertThat(Listing.builder().build(), isA(Listing.class));
    }

    @Test
    public void when_UsingLombokBuilder_Then_PropertiesShouldMatch() {
        String title = "some title";
        Store store = new Store();
        store.setId(1L);

        Listing listing = Listing.builder()
            .title(title)
            .store(store)
            .build();

        assertEquals(listing.getTitle(), title);
        assertEquals(listing.getStore(), store);
    }
}
