package org.dbiagi.marketplace;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;
import java.util.Locale;

@SpringBootTest(classes = Application.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public abstract class BaseSpringRunner {
    protected final static String ACCOUNTS_URI = "/accounts";
    protected final static String LISTINGS_URI = "/listings";
    protected final static String CATEGORIES_URI = "/categories";
    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    protected MockMvc mvc;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected static Faker faker;

    @BeforeAll
    public static void setup() {
        faker = new Faker(Locale.getDefault());
    }

    protected String basicAuth(String user, String password) {
        String userAndPassword = user + ":" + password;

        return "Basic " + Base64.getEncoder().encodeToString(userAndPassword.getBytes());
    }
}

