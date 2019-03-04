package org.dbiagi.marketplace.entity;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class AccountTest extends EntityTest {
    public void given_ValidUsername_ShouldNot_ReturnConstraintError(){
        Account account = new Account();
        account.setUsername("valid_user_name_123");

        Set<ConstraintViolation<Account>> violations = validator.validate(account);
    }
}
