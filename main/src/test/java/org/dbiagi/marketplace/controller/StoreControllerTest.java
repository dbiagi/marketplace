package org.dbiagi.marketplace.controller;

import org.dbiagi.marketplace.DatabaseSeed;
import org.dbiagi.marketplace.entity.Store;
import org.dbiagi.marketplace.entity.User;
import org.dbiagi.marketplace.validation.ValidationError;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@Tag("controller")
public class StoreControllerTest extends BaseWebTest {

    private Store getValidStore() {
        Store store = new Store();

        store.setEmail(faker.internet().emailAddress());
        store.setName(faker.lorem().sentence(2));
        store.setPhone(faker.phoneNumber().phoneNumber());
        store.setCellphone(faker.phoneNumber().cellPhone());
        store.setZipCode(faker.address().zipCode());
        store.setAddress(faker.address().streetAddress());
        store.setNeighborhood(null);

        return store;
    }

    private Store getInvalidStore() {
        return new Store();
    }

    private ResponseEntity<Store> postStore() {
        String uri = "/api/v1/stores";

        return restTemplate
                .postForEntity(uri, getValidStore(), Store.class);
    }

    @Test
    public void testUsersList() {
        String uri = String.format("/api/v1/stores/%d/users", faker.number().numberBetween(1, DatabaseSeed.STORES - 1));

        ResponseEntity<List<User>> response = restTemplate
                .withBasicAuth(User.Role.STORE_ATTENDANT.name(), AUTH_PASSWORD)
                .exchange(uri, HttpMethod.GET, null, userListReference);

        assertTrue("request status return 2xx", response.getStatusCode().is2xxSuccessful());

        assertThat("is Resource instance", response.getBody(), isA(List.class));
    }

    @Test
    public void testList() {
        String uri = "/api/v1/stores";

        ResponseEntity<List<Store>> response = restTemplate
                .withBasicAuth(User.Role.STORE_ATTENDANT.name(), AUTH_PASSWORD)
                .exchange(uri, HttpMethod.GET, null, storeListReference);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertTrue(response.getBody().size() > 0);
    }

    @Test
    public void testGet() {
        String uri = String.format("/api/v1/stores/%d", faker.number().numberBetween(1, DatabaseSeed.STORES));

        ResponseEntity<Store> response = restTemplate
                .withBasicAuth(User.Role.STORE_ATTENDANT.name(), AUTH_PASSWORD)
                .exchange(uri, HttpMethod.GET, null, Store.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertThat(response.getBody(), is(not(nullValue())));
    }

    @Test
    public void testValidPut() {
        Long id = faker.number().numberBetween(1, DatabaseSeed.STORES - 1L);

        String uri = String.format("/api/v1/stores/%d", id);
        HashMap<String, String> fields = new HashMap<>();
        fields.put("name", "New name");

        ResponseEntity<String> response = restTemplate
                .withBasicAuth(User.Role.STORE_OWNER.name(), AUTH_PASSWORD)
                .exchange(uri, HttpMethod.PUT, new HttpEntity<>(getValidStore()), String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testInvalidPut() {
        Long id = faker.number().numberBetween(1, DatabaseSeed.STORES - 1L);

        String uri = String.format("/api/v1/stores/%d", id);

        Map<String, String> fields = new HashMap<>();
        fields.put("name", "");

        ResponseEntity<List<ValidationError>> response = restTemplate
                .withBasicAuth(User.Role.STORE_OWNER.name(), AUTH_PASSWORD)
                .exchange(uri, HttpMethod.PUT, new HttpEntity<>(fields), validationErrorListReference);

        assertTrue(response.getStatusCode().is4xxClientError());

        assertNotNull(response.getBody());

        assertTrue(response.getBody().size() > 0);
    }

    @Test
    public void testUnauthorizedPut() {
        String uri = String.format("/api/v1/stores/%d", faker.number().numberBetween(1, DatabaseSeed.STORES - 1L));

        Map<String, String> fields = new HashMap<>();
        fields.put("name", "test");

        ResponseEntity response = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(fields), Void.class);

        assertEquals(response.getStatusCodeValue(), HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void testValidPost() {
        ResponseEntity<Store> response = postStore();

        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertThat(response.getBody(), isA(Store.class));
    }

    @Test
    public void testInvalidPost() {
        String uri = "/api/v1/stores";

        ResponseEntity<List<ValidationError>> response = restTemplate
                .exchange(uri, HttpMethod.POST, new HttpEntity<>(getInvalidStore()), validationErrorListReference);

        assertTrue(response.getStatusCode().is4xxClientError());

        assertTrue(response.getBody().size() > 0);
    }

    @Test
    public void testResourceNotFoundHandler() {
        String uri = String.format(
                "/api/v1/stores/%d",
                DatabaseSeed.STORES + 10
        );

        ResponseEntity<String> response = restTemplate
                .withBasicAuth(User.Role.STORE_ATTENDANT.name(), AUTH_PASSWORD)
                .getForEntity(uri, String.class);

        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    public void testValidDelete() {
        Store store = postStore().getBody();

        String uri = String.format("/api/v1/stores/%d", store.getId());

        ResponseEntity<Void> response = restTemplate
                .withBasicAuth(User.Role.STORE_OWNER.name(), AUTH_PASSWORD)
                .exchange(uri, HttpMethod.DELETE, null, Void.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertFalse(response.hasBody());
    }

    @Test
    public void testForbiddenDelete() {
        String uri = String.format(
                "/api/v1/stores/%d",
                faker.number().numberBetween(1, DatabaseSeed.STORES)
        );

        ResponseEntity<Void> response = restTemplate
                .exchange(uri, HttpMethod.DELETE, null, Void.class);

        assertEquals(response.getStatusCodeValue(), HttpStatus.UNAUTHORIZED.value());
    }
}
