package org.dbiagi.marketplace.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dbiagi.marketplace.BaseSpringRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

@AutoConfigureMockMvc
abstract class DataRestTest extends BaseSpringRunner {
    final static String ACCOUNTS_URI = "/accounts";
    final static String LISTINGS_URI = "/listings";
    final static String CATEGORIES_URI = "/categories";
    final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mvc;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    String basicAuth(String user, String password) {
        String userAndPassword = user + ":" + password;

        return "Basic " + Base64.getEncoder().encodeToString(userAndPassword.getBytes());
    }
}
