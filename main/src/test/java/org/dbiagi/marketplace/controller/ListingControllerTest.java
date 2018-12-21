package org.dbiagi.marketplace.controller;

import org.dbiagi.marketplace.dto.ListingDTO;
import org.dbiagi.marketplace.entity.Account;
import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.repository.validation.ValidationError;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@Tag("controller")
public class ListingControllerTest extends BaseWebTest {

    private final String URI = "/api/v1/listings";

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ListingDTO getValidListing() {
        return ListingDTO.builder()
            .title(faker.lorem().sentence(1))
            .active(true)
            .slug(faker.internet().slug())
            .shortDescription(faker.lorem().sentence(2))
            .longDescription(faker.lorem().sentence(5))
            .tags(new LinkedList<>())
            .categories(getCategories())
            .build();
    }

    private ListingDTO getInvalidListing() {
        return new ListingDTO();
    }

    private List<Long> getCategories() {
        return Arrays.asList(1L, 3L);
    }

    private ResponseEntity<ListingDTO> postListing() {
        return restTemplate
            .withBasicAuth(Account.Role.STORE_ATTENDANT.name(), AUTH_PASSWORD)
            .exchange(URI, HttpMethod.POST, new HttpEntity<>(getValidListing()), ListingDTO.class);
    }

    @Test
    public void testGet() {
        String uri = String.format("%s/featured", URI);

        ResponseEntity<List<Listing>> response = restTemplate
            .exchange(uri, HttpMethod.GET, null, listingListReference);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertTrue(response.getBody().size() > 0);
    }

    @Test
    public void testGetNotFound() {
        String uri = String.format("%s/%d", URI, 0);

        ResponseEntity<Void> response = restTemplate
            .exchange(uri, HttpMethod.GET, null, Void.class);

        assertSame(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testValidPut() {
        String uri = String.format("%s/%d", URI, 1);

        ResponseEntity<String> response = restTemplate
            .withBasicAuth(Account.Role.STORE_ATTENDANT.name(), AUTH_PASSWORD)
            .exchange(uri, HttpMethod.PUT, new HttpEntity<>(getValidListing()), String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testInvalidPut() {
        String uri = String.format("%s/%d", URI, 1);

        ResponseEntity<String> response = restTemplate
            .withBasicAuth(Account.Role.STORE_ATTENDANT.name(), AUTH_PASSWORD)
            .exchange(uri, HttpMethod.PUT, new HttpEntity<>(getInvalidListing()), String.class);

        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    public void testValidPost() {
        ResponseEntity<ListingDTO> response = postListing();

        logger.info(response.toString());

        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertNotNull(response.getBody().getId());
    }

    @Test
    public void testInvalidPost() {
        ResponseEntity<List<ValidationError>> response = restTemplate
            .withBasicAuth(Account.Role.STORE_ATTENDANT.name(), AUTH_PASSWORD)
            .exchange(URI, HttpMethod.POST, new HttpEntity<>(getInvalidListing()), validationErrorListReference);

        assertTrue(response.getStatusCode().is4xxClientError());

        assertTrue(!response.getBody().isEmpty());
    }

    @Test(expected = ResourceAccessException.class)
    public void testForbiddenPost() {
        restTemplate
            .withBasicAuth(Account.Role.STORE_ATTENDANT.name(), "wrong-password")
            .exchange(URI, HttpMethod.POST, new HttpEntity<>(getValidListing()), String.class);
    }

    @Test
    public void testValidDelete() {
        ResponseEntity<ListingDTO> response = postListing();

        String uri = String.format("%s/%d", URI, response.getBody().getId());

        ResponseEntity<String> deleteResponse = restTemplate
            .withBasicAuth(Account.Role.STORE_OWNER.name(), AUTH_PASSWORD)
            .exchange(uri, HttpMethod.DELETE, null, String.class);

        assertSame(deleteResponse.getStatusCode(), HttpStatus.OK);
    }
}
