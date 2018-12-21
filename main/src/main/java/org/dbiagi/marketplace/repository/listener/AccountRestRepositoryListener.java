package org.dbiagi.marketplace.repository.listener;

import lombok.extern.log4j.Log4j2;
import org.dbiagi.marketplace.entity.Account;
import org.dbiagi.marketplace.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AccountRestRepositoryListener extends AbstractRepositoryEventListener<Account> {

    private AccountService accountService;

    @Autowired
    AccountRestRepositoryListener(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void onBeforeCreate(Account account) {
        accountService.prepare(account);
        log.info("Creating account", account);
    }
}
