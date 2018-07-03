package org.dbiagi.marketplace.controller;

import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.entity.Store;
import org.dbiagi.marketplace.entity.User;
import org.dbiagi.marketplace.entity.classification.Category;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertTrue;

@Tag("controller")
public class ListingControllerTest extends BaseWebTest {

    private final String URI = "/api/v1/listings";

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Listing getValidListing() {
        return Listing.builder()
            .store(new Store())
            .title(faker.lorem().sentence(1))
            .active(true)
            .slug("test")
            .shortDescription(faker.lorem().sentence(2))
            .longDescription(faker.lorem().sentence(5))
            .tags(new HashSet<>())
            .categories(getCategories())
            .build();
    }

    private HashSet<Category> getCategories() {
        HashSet<Category> categories = new HashSet<>();
        Category c1 = new Category();
        c1.setId(1);
        Category c2 = new Category();
        c2.setId(3);

        categories.add(c1);
        categories.add(c2);

        return categories;
    }

    @Test
    public void testGet() {
        String uri = String.format("%s/featured", URI);

        ResponseEntity<List<Listing>> response = restTemplate
            .withBasicAuth(User.Role.STORE_OWNER.name(), AUTH_PASSWORD)
            .exchange(uri, HttpMethod.GET, null, listingListReference);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertTrue(response.getBody().size() > 0);
    }

    @Test
    public void testPut() {
        String uri = String.format("%s/%d", URI, 1);

        ResponseEntity<Void> response = restTemplate
            .withBasicAuth(User.Role.STORE_ATTENDANT.name(), AUTH_PASSWORD)
            .exchange(uri, HttpMethod.PUT, new HttpEntity<>(getValidListing()), Void.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}
