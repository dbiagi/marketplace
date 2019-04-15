package org.dbiagi.marketplace.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.*;

@Tag("model")
class UserTest extends EntityTest {

    @Test
    void given_StoreThatHaveUsers_When_SerializeToJson_ShouldNotThrowException()
        throws JsonProcessingException {
        Account account = new Account();
        Store store = new Store();

        account.setName("Diego");
        account.setUsername("diego");
        account.setEmail("diego@bol.com.br");

        store.setName("StoreInterface 1");
        store.setEmail("store@bol.com.br");

        assertNotNull(new ObjectMapper().writeValueAsString(store));
        assertNotNull(new ObjectMapper().writeValueAsString(account));
    }

    @Test
    void given_InvalidUser_When_Validating_Then_ViolationArrayShouldNotBeEmpty() {
        Account account = new Account();

        Set<ConstraintViolation<Account>> result = validator.validate(account);

        assertTrue(result.size() >= 3);
    }

    @Test
    void given_ValidUser_When_Validating_Then_ViolationArrayShouldBeEmpty() {
        Account account = new Account();

        account.setName(faker.name().name());
        account.setUsername(faker.name().username());
        account.setEmail(faker.internet().emailAddress());
        account.setPassword(faker.internet().password());
        account.setRole(Account.Role.ROLE_USER);

        Set<ConstraintViolation<Account>> result = validator.validate(account);

        assertEquals(0, result.size());
    }
}
