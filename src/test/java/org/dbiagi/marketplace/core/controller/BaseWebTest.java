package org.dbiagi.marketplace.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class BaseWebTest {

    final String AUTH_USERNAME = "SUPER_ADMIN";
    final String AUTH_PASSWORD = "123";

    ObjectMapper mapper;

    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    Faker faker;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }
}
