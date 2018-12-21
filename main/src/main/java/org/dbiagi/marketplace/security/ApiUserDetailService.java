package org.dbiagi.marketplace.security;

import org.dbiagi.marketplace.entity.Account;
import org.dbiagi.marketplace.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApiUserDetailService implements UserDetailsService {

    private AccountService accountService;

    @Autowired
    ApiUserDetailService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.findByEmailOrUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException(String.format("Account not found for email %s", username));
        }

        return account;
    }
}
