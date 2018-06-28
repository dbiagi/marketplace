package org.dbiagi.marketplace.controller;

import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.entity.User;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertTrue;

@Tag("controller")
public class ListingControllerTest extends BaseWebTest {

    private final String URI = "/api/v1/listings";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testGet() {
        String uri = String.format("%s/featured", URI);

        ResponseEntity<List<Listing>> response = restTemplate
            .withBasicAuth(User.Role.STORE_OWNER.name(), AUTH_PASSWORD)
            .exchange(uri, HttpMethod.GET, null, listingListReference);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertTrue(response.getBody().size() > 0);
    }
}
