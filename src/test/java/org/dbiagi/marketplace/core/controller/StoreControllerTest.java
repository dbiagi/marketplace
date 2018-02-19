package org.dbiagi.marketplace.core.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.javafaker.Faker;
import org.dbiagi.marketplace.DatabaseSeed;
import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.entity.User;
import org.dbiagi.marketplace.core.response.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;

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

        Assert.assertTrue("does request status return 2xx", response.getStatusCode().is2xxSuccessful());

        Resource o = mapper.readValue((String) response.getBody(), new TypeReference<Resource<List<User>>>() {
        });

        Assert.assertThat("is Resource instance", o, isA(Resource.class));
    }


    @Test
    public void testGetById() throws IOException {
        String uri = String.format("/stores/%d", faker.number().numberBetween(1, DatabaseSeed.STORES));

        ResponseEntity response = restTemplate
                .withBasicAuth(USERNAME, PASSWORD)
                .getForEntity(uri, String.class);

        Assert.assertTrue(response.getStatusCode().is2xxSuccessful());

        Resource<Store> resource = mapper.readValue((String) response.getBody(), new TypeReference<Resource<Store>>() {
        });

        Assert.assertThat(resource.getData(), is(not(nullValue())));
    }

    @Test
    public void testUpdate() throws IOException {
        Long id = faker.number().numberBetween(1, DatabaseSeed.STORES - 1L);
        String uri = String.format("/stores/%d", id);
        String email = faker.internet().emailAddress();

        Store store = new Store();
        store.setEmail(email);
        ResponseEntity<String> response = restTemplate
                .withBasicAuth(USERNAME, PASSWORD)
                .exchange(uri, HttpMethod.PUT, null, String.class);

        Assert.assertTrue(response.getStatusCode().is2xxSuccessful());

        Resource<Store> responseObj = mapper.readValue(response.getBody(), new TypeReference<Resource<Store>>() {
        });

        Assert.assertTrue(email.equals(responseObj.getData().getEmail()));
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

        Assert.assertTrue(response.getStatusCode().is4xxClientError());
    }


}
