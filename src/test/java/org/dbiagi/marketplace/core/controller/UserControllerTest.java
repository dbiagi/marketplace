package org.dbiagi.marketplace.core.controller;

import org.dbiagi.marketplace.DatabaseSeed;
import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.entity.User;
import org.dbiagi.marketplace.core.response.ValidationErrorResponse;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@Tag("controller")
public class UserControllerTest extends BaseWebTest {

    @Test
    public void testGet() {
        String uri = String.format("/users/%d", faker.number().numberBetween(1, DatabaseSeed.USERS));

        ResponseEntity<User> response = restTemplate
                .withBasicAuth(AUTH_USERNAME, AUTH_PASSWORD)
                .getForEntity(uri, User.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertThat(response.getBody(), isA(User.class));
    }

    @Test
    public void testValidPost() {
        String uri = "/users";

        ResponseEntity<String> response = restTemplate
                .withBasicAuth(AUTH_USERNAME, AUTH_PASSWORD)
                .postForEntity(uri, getValidUser(), String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        //assertThat(response.getBody(), isA(User.class));
    }

    @Test
    public void testInvalidPost() {
        String uri = "/users";

        ResponseEntity<ValidationErrorResponse> response = restTemplate
                .withBasicAuth(AUTH_USERNAME, AUTH_PASSWORD)
                .postForEntity(uri, getInvalidUser(), ValidationErrorResponse.class);

        assertTrue(response.getStatusCode().is4xxClientError());

        assertThat(response.getBody(), isA(ValidationErrorResponse.class));
    }

    public void testPut() {

    }


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
}
