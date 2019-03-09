package org.dbiagi.marketplace.entity;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class AccountTest extends EntityTest {

    private Account getValidAccount() {
        Account account = new Account();

        account.setUsername(faker.name().username());
        account.setPassword(faker.internet().password());
        account.setName(faker.name().name());
        account.setEmail(faker.internet().emailAddress());
        account.setRole(Account.Role.ADMIN);

        return account;
    }

    private Account getInvalidAccount() {
        return new Account();
    }

    @Test
    void givenValidAccountShouldNotReturnViolationsWhenValidating() {
        Set<ConstraintViolation<Account>> violations = validator.validate(getValidAccount());

        assertTrue(violations.isEmpty());
    }

    @Test
    void givenInvalidAccountShouldReturnViolationsWhenValidating() {
        Set<ConstraintViolation<Account>> violations = validator.validate(getInvalidAccount());

        assertFalse(violations.isEmpty());
    }
}
