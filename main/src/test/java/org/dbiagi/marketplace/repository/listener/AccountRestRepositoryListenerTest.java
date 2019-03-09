package org.dbiagi.marketplace.repository.listener;

import org.dbiagi.marketplace.BaseSpringRunner;
import org.dbiagi.marketplace.entity.Account;
import org.dbiagi.marketplace.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertFalse;

class AccountRestRepositoryListenerTest extends BaseSpringRunner {

    @Autowired
    private AccountService accountService;

    @Test
    void testBeforeCreate() {
        AccountRestRepositoryListener listener = new AccountRestRepositoryListener(accountService);

        Account account = new Account();
        account.setPlainPassword("some-password");

        listener.onBeforeCreate(account);

        assertFalse(account.getPassword().isEmpty());
    }
}
