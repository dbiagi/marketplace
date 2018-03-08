package org.dbiagi.marketplace.core.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.javafaker.Faker;
import org.dbiagi.marketplace.DatabaseSeed;
import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.entity.User;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@Tag("controller")
public class StoreControllerTest extends BaseWebTest {

    private final String USERNAME = "diego";
    private final String PASSWORD = "123";
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private Faker faker;

    @Test
    public void testFindAll() throws IOException {
        String uri = String.format("/stores/%d/users", faker.number().numberBetween(1, DatabaseSeed.STORES - 1));

        ResponseEntity response = restTemplate
                .withBasicAuth(USERNAME, PASSWORD)
                .getForEntity(uri, String.class);

        assertTrue("does request status return 2xx", response.getStatusCode().is2xxSuccessful());

        List<User> o = mapper.readValue((String) response.getBody(), new TypeReference<List<User>>() {
        });

        assertThat("is Resource instance", o, isA(List.class));
    }


    @Test
    public void testGetById() throws IOException {
        String uri = String.format("/stores/%d", faker.number().numberBetween(1, DatabaseSeed.STORES));

        ResponseEntity response = restTemplate
                .withBasicAuth(USERNAME, PASSWORD)
                .getForEntity(uri, String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        Store store = mapper.readValue((String) response.getBody(), Store.class);

        assertThat(store, is(not(nullValue())));
    }

    @Test
    public void testUpdate() throws IOException {
        Long id = faker.number().numberBetween(1, DatabaseSeed.STORES - 1L);
        String uri = String.format("/stores/%d", id);

        ResponseEntity<String> response = restTemplate
                .withBasicAuth(USERNAME, PASSWORD)
                .exchange(uri, HttpMethod.PUT, new HttpEntity<>(getValidStore()), String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        assertNull(response.getBody());
    }


    @Test
    public void testResourceNotFoundHandler() {
        String uri = String.format(
                "/stores/%d",
                faker.number().numberBetween(DatabaseSeed.STORES, DatabaseSeed.STORES + 10)
        );

        ResponseEntity<String> response = restTemplate
                .withBasicAuth(USERNAME, PASSWORD)
                .getForEntity(uri, String.class);

        assertTrue(response.getStatusCode().is4xxClientError());
    }

    private Store getValidStore() {
        Store store = new Store();

        store.setEmail(faker.internet().emailAddress());
        store.setName(faker.lorem().sentence(2));
        store.setCnpj(faker.numerify("############"));
        store.setPhone(faker.phoneNumber().phoneNumber());
        store.setCellphone(faker.phoneNumber().cellPhone());

        return store;
    }

}
