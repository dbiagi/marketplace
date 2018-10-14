package org.dbiagi.marketplace;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.dbiagi.marketplace.entity.Store;
import org.dbiagi.marketplace.entity.User;
import org.dbiagi.marketplace.model.UserInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserRestRepositoryTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mvc;

    private Faker faker;

    private User getValidUser() {
        return User.builder()
            .name(faker.name().name())
            .username(faker.name().username())
            .role(User.Role.STORE_ATTENDANT)
            .store(new Store())
            .cellphone(faker.phoneNumber().cellPhone())
            .email(faker.internet().emailAddress())
            .plainPassword(faker.internet().password())
            .build();
    }

    @Before
    public void setup() {
        faker = new Faker(Locale.getDefault());
    }

    @Test
    public void givenValidUserWhenPostingItShouldReturnCreatedStatus() throws Exception {
        mvc.perform(post("/stores/1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(getValidUser())))
            .andExpect(status().isCreated());
    }
}
