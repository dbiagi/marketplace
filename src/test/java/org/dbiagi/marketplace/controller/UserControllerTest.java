package org.dbiagi.marketplace.controller;

import org.dbiagi.marketplace.DatabaseSeed;
import org.dbiagi.marketplace.entity.Store;
import org.dbiagi.marketplace.entity.User;
import org.dbiagi.marketplace.validation.ValidationError;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.*;

@Tag("controller")
public class UserControllerTest extends BaseWebTest {

    private final String URI = "/api/v1/users";

    private User getInvalidUser() {
        return new User();
    }

    private User getValidUser() {
        User user = new User();

        user.setRole(User.Role.ADMIN);
        user.setPassword(faker.internet().password());
        user.setName(faker.name().fullName());
        user.setUsername(faker.name().username());
        user.setEmail(faker.internet().emailAddress());
        user.setStore(new Store());

        return user;
    }

    @Test
    public void testValidGet() {
        String uri = String.format("%s/%d", URI, faker.number().numberBetween(1, DatabaseSeed.USERS));

        ResponseEntity<User> response = restTemplate
                .withBasicAuth(User.Role.STORE_ATTENDANT.name(), AUTH_PASSWORD)
                .getForEntity(uri, User.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertThat(response.getBody(), isA(User.class));
    }

    @Test
    public void testInvalidGet() {
        String uri = String.format("%s/%d", URI, 0);

        ResponseEntity response = restTemplate
                .withBasicAuth(User.Role.STORE_ATTENDANT.name(), AUTH_PASSWORD)
                .exchange(uri, HttpMethod.GET, null, String.class);

        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    public void testValidPost() {
        ResponseEntity<User> response = restTemplate
                .withBasicAuth(User.Role.STORE_OWNER.name(), AUTH_PASSWORD)
                .postForEntity(URI, getValidUser(), User.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertThat(response.getBody(), isA(User.class));
    }

    @Test
    public void testInvalidPost() {
        ResponseEntity<List<ValidationError>> response = restTemplate
                .withBasicAuth(User.Role.STORE_OWNER.name(), AUTH_PASSWORD)
                .exchange(URI, HttpMethod.POST, new HttpEntity<>(getInvalidUser()), validationErrorListReference);

        assertTrue(response.getStatusCode().is4xxClientError());

        assertTrue(response.getBody().size() > 0);
    }

    @Test
    public void testDelete() {
        String uri = String.format("%s/%d", URI, faker.number().numberBetween(1, DatabaseSeed.USERS));

        ResponseEntity<Void> response = restTemplate
                .withBasicAuth(User.Role.STORE_OWNER.name(), AUTH_PASSWORD)
                .exchange(uri, HttpMethod.DELETE, null, Void.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testValidPut() {
        String uri = String.format("%s/%d", URI, faker.number().numberBetween(1, DatabaseSeed.USERS));

        Map<String, String> fields = new HashMap<>();
        fields.put("name", "New name");

        ResponseEntity<Void> response = restTemplate
                .withBasicAuth(User.Role.STORE_OWNER.name(), AUTH_PASSWORD)
                .exchange(uri, HttpMethod.PUT, new HttpEntity<>(fields), Void.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertNull(response.getBody());
    }

    @Test
    public void testInvalidPut() {
        String uri = String.format("%s/%d", URI, faker.number().numberBetween(1, DatabaseSeed.USERS));

        Map<String, String> fields = new HashMap<>();
        fields.put("name", "");

        ResponseEntity<List<ValidationError>> response = restTemplate
                .withBasicAuth(User.Role.STORE_OWNER.name(), AUTH_PASSWORD)
                .exchange(uri, HttpMethod.PUT, new HttpEntity<>(fields), validationErrorListReference);

        assertTrue(response.getStatusCode().is4xxClientError());

        assertTrue(response.getBody().size() > 0);

        assertEquals("name", response.getBody().get(0).getField());
    }

}
