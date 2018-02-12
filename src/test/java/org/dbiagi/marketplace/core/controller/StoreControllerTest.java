package org.dbiagi.marketplace.core.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.javafaker.Faker;
import org.dbiagi.marketplace.DatabaseSeed;
import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.entity.User;
import org.dbiagi.marketplace.core.response.EntityResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
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

        EntityResponse o = mapper.readValue((String) response.getBody(), new TypeReference<EntityResponse<List<User>>>() {
        });

        Assert.assertThat("is EntityResponse instance", o, isA(EntityResponse.class));
    }


    @Test
    public void testGetById() throws IOException {
        String uri = String.format("/stores/%d", faker.number().numberBetween(1, DatabaseSeed.STORES));

        ResponseEntity response = restTemplate
                .withBasicAuth(USERNAME, PASSWORD)
                .getForEntity(uri, String.class);

        Assert.assertTrue(response.getStatusCode().is2xxSuccessful());

        EntityResponse<Store> entityResponse = mapper.readValue((String) response.getBody(), new TypeReference<EntityResponse<Store>>() {
        });

        Assert.assertThat(entityResponse.getData(), is(not(nullValue())));
    }


}
