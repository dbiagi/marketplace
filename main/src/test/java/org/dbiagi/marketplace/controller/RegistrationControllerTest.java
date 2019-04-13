package org.dbiagi.marketplace.controller;

import org.dbiagi.marketplace.BaseSpringRunner;
import org.dbiagi.marketplace.entity.dto.AccountRegistration;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RegistrationControllerTest extends BaseSpringRunner {

    private AccountRegistration getValidRegisterAccount() {
        return AccountRegistration.builder()
            .email(faker.internet().emailAddress())
            .name(faker.name().name())
            .username(faker.name().username())
            .plainPassword(faker.internet().password())
            .build();
    }

    private AccountRegistration getInvalidRegisterAccount() {
        return AccountRegistration.builder()
            .name(faker.name().name())
            .username(faker.name().username())
            .plainPassword(faker.internet().password())
            .build();
    }

    @Test
    void given_ValidAccountRegistration_Should_ReturnSuccess() throws Exception {
        mvc.perform(
            post(RegistrationController.REGISTRATION_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getValidRegisterAccount())))
            .andExpect(status().isCreated());
    }

    @Test
    void given_InvalidAccountRegistration_Should_ReturnBadRequest() throws Exception {
        var content = mvc.perform(
            post(RegistrationController.REGISTRATION_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getInvalidRegisterAccount())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors[*].field", contains("email")))
            .andReturn().getResponse().getContentAsString();

        logger.info(content);
    }
}