package org.dbiagi.marketplace.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.Tag;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.*;

@Tag("model")
public class UserTest extends EntityTest {

    @Test
    public void given_StoreThatHaveUsers_When_SerializeToJson_ShouldNotThrowException()
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
    public void given_InvalidUser_When_Validating_Then_ViolationArrayShouldNotBeEmpty() {
        Account account = new Account();

        Set<ConstraintViolation<Account>> result = validator.validate(account);

        assertTrue(result.size() >= 3);
    }

    @Test
    public void given_ValidUser_When_Validating_Then_ViolationArrayShouldBeEmpty() {
        Account account = new Account();

        account.setName(faker.name().name());
        account.setUsername(faker.name().username());
        account.setEmail(faker.internet().emailAddress());
        account.setPassword(faker.internet().password());
        account.setRole(Account.Role.ADMIN);

        Set<ConstraintViolation<Account>> result = validator.validate(account);

        assertEquals(0, result.size());
    }
}
