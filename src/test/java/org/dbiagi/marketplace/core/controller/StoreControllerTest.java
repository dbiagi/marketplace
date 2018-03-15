package org.dbiagi.marketplace.core.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.dbiagi.marketplace.DatabaseSeed;
import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.entity.User;
import org.dbiagi.marketplace.core.response.ResourceNotFound;
import org.dbiagi.marketplace.core.response.ValidationErrorResponse;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@Tag("controller")
public class StoreControllerTest extends BaseWebTest {

    @Test
    public void testUsersList() throws IOException {
        String uri = String.format("/stores/%d/users", faker.number().numberBetween(1, DatabaseSeed.STORES - 1));

        ResponseEntity response = restTemplate
                .withBasicAuth(AUTH_USERNAME, AUTH_PASSWORD)
                .getForEntity(uri, String.class);

        assertTrue("does request status return 2xx", response.getStatusCode().is2xxSuccessful());

        List<User> o = mapper.readValue((String) response.getBody(), new TypeReference<List<User>>() {
        });

        assertThat("is Resource instance", o, isA(List.class));
    }

    @Test
    public void testList() {
        String uri = "/stores";

        ResponseEntity<List<User>> response = restTemplate
                .withBasicAuth(AUTH_USERNAME, AUTH_PASSWORD)
                .exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                });

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }


    @Test
    public void testGet() throws IOException {
        String uri = String.format("/stores/%d", faker.number().numberBetween(1, DatabaseSeed.STORES));

        ResponseEntity response = restTemplate
                .withBasicAuth(AUTH_USERNAME, AUTH_PASSWORD)
                .getForEntity(uri, String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        Store store = mapper.readValue((String) response.getBody(), Store.class);

        assertThat(store, is(not(nullValue())));
    }

    @Test
    public void testValidPut() {
        Long id = faker.number().numberBetween(1, DatabaseSeed.STORES - 1L);
        String uri = String.format("/stores/%d", id);

        ResponseEntity<String> response = restTemplate
                .withBasicAuth(AUTH_USERNAME, AUTH_PASSWORD)
                .exchange(uri, HttpMethod.PUT, new HttpEntity<>(getValidStore()), String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertNull(response.getBody());
    }

    @Test
    @Ignore
    public void testInvalidPut() {
        Long id = faker.number().numberBetween(1, DatabaseSeed.STORES - 1L);
        String uri = String.format("/stores/%d", id);

        ResponseEntity<ValidationErrorResponse> response = restTemplate
                .withBasicAuth(AUTH_USERNAME, AUTH_PASSWORD)
                .exchange(uri, HttpMethod.PUT, new HttpEntity<>(getInvalidStore()), ValidationErrorResponse.class);

        assertTrue(response.getStatusCode().is4xxClientError());

        assertThat(response.getBody(), isA(ValidationErrorResponse.class));

        assertTrue(response.getBody().getErrors().size() > 0);
    }

    @Test
    public void testValidPost() {
        String uri = "/stores";

        ResponseEntity<Store> response = restTemplate
                .postForEntity(uri, getValidStore(), Store.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertThat(response.getBody(), isA(Store.class));
    }

    @Test
    public void testInvalidPost() {
        String uri = "/stores";

        // Given an invalid entity
        ResponseEntity<ValidationErrorResponse> response = restTemplate
                .postForEntity(uri, getInvalidStore(), ValidationErrorResponse.class);

        assertTrue(response.getStatusCode().is4xxClientError());

        assertThat(response.getBody(), isA(ValidationErrorResponse.class));

        assertTrue(response.getBody().getErrors().size() > 0);
    }


    @Test
    public void testResourceNotFoundHandler() {
        String uri = String.format(
                "/stores/%d",
                DatabaseSeed.STORES + 10
        );

        ResponseEntity<String> response = restTemplate
                .withBasicAuth(AUTH_USERNAME, AUTH_PASSWORD)
                .getForEntity(uri, String.class);

        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    public void testValidDelete() throws URISyntaxException {
        String uri = String.format(
                "/stores/%d",
                faker.number().numberBetween(1, DatabaseSeed.STORES)
        );

        RequestEntity<String> request = new RequestEntity<>("", HttpMethod.DELETE, new URI(uri));

        ResponseEntity<Void> response = restTemplate
                .withBasicAuth(AUTH_USERNAME, AUTH_PASSWORD)
                .exchange(uri, HttpMethod.DELETE, request, Void.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertFalse(response.hasBody());
    }

    @Test
    public void testInvalidDelete() throws Exception {
        String uri = String.format("/stores/%d", 1000);

        RequestEntity<String> request = new RequestEntity<>("", HttpMethod.DELETE, new URI(uri));

        ResponseEntity<ResourceNotFound> response = restTemplate
                .withBasicAuth(AUTH_USERNAME, AUTH_PASSWORD)
                .exchange(uri, HttpMethod.DELETE, request, ResourceNotFound.class);

        assertTrue(response.getStatusCode().is4xxClientError());

        assertThat(response.getBody(), isA(ResourceNotFound.class));
    }

    private Store getValidStore() {
        Store store = new Store();

        store.setEmail(faker.internet().emailAddress());
        store.setName(faker.lorem().sentence(2));
        store.setCnpj(faker.numerify("############"));
        store.setPhone(faker.phoneNumber().phoneNumber());
        store.setCellphone(faker.phoneNumber().cellPhone());
        store.setZipCode(faker.address().zipCode());
        store.setAddress(faker.address().streetAddress());

        return store;
    }

    private Store getInvalidStore() {
        return new Store();
    }

}
