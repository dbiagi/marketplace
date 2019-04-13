package org.dbiagi.marketplace.repository;

import org.dbiagi.marketplace.BaseSpringRunner;
import org.dbiagi.marketplace.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountRestRepositoryTest extends BaseSpringRunner {

    @Autowired
    EntityManager entityManager;

    private Account getValidAccount() {
        Account account = new Account();
        account.setName(faker.name().fullName());
        account.setUsername(faker.name().username());
        account.setEmail(faker.internet().emailAddress());
        account.setPassword(faker.internet().password());
        account.setRole(Account.Role.STORE_ATTENDANT);

        return account;
    }

    private Account getInvalidAccount() {
        return new Account();
    }

    @Test
    void givenValidUserWhenPostingItShouldReturnCreatedStatus() throws Exception {
        Account account = getValidAccount();

        String response = mvc.perform(post(ACCOUNTS_URI)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(account)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.email").value(account.getEmail()))
            .andReturn().getResponse().getContentAsString();

        logger.info(response);
    }

    @Test
    void givenInvalidUserWhenPostingItShouldReturnErrorResponse() throws Exception {
        String response = mvc.perform(post(ACCOUNTS_URI)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(getInvalidAccount())))
            .andExpect(status().is4xxClientError())
            .andReturn().getResponse().getContentAsString();

        logger.info(response);
    }

    @Test
    void whenGetAccountListItShouldReturnContent() throws Exception {
        String response = mvc.perform(get(ACCOUNTS_URI)
            .contentType(APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful())
            .andReturn().getResponse().getContentAsString();

        logger.info(response);
    }
}
