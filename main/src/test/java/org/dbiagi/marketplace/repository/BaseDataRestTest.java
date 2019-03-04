package org.dbiagi.marketplace.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dbiagi.marketplace.Application;
import org.dbiagi.marketplace.BaseSpringRunner;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public abstract class BaseDataRestTest extends BaseSpringRunner {
    final static String ACCOUNTS_URI = "/accounts";
    final static String LISTINGS_URI = "/listings";
    final static String CATEGORIES_URI = "/categories";
    final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mvc;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public String basicAuth(String user, String password) {
        String userAndPassword = user + ":" + password;

        return "Basic " + Base64.getEncoder().encodeToString(userAndPassword.getBytes());
    }
}
