package org.dbiagi.marketplace.controller;

import com.github.javafaker.Faker;
import org.dbiagi.marketplace.entity.Store;
import org.dbiagi.marketplace.entity.User;
import org.dbiagi.marketplace.validation.ValidationError;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class BaseWebTest {

    final String AUTH_PASSWORD = "123";

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    Faker faker;

    ParameterizedTypeReference<List<ValidationError>> validationErrorListReference;

    ParameterizedTypeReference<List<User>> userListReference;

    ParameterizedTypeReference<List<Store>> storeListReference;

    @Before
    public void setUp() {
        validationErrorListReference = new ParameterizedTypeReference<List<ValidationError>>() {
        };
        userListReference = new ParameterizedTypeReference<List<User>>() {
        };

        storeListReference = new ParameterizedTypeReference<List<Store>>() {
        };
    }
}