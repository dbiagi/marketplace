package org.dbiagi.marketplace.security;

import org.dbiagi.marketplace.entity.Account;
import org.dbiagi.marketplace.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApiUserDetailService implements UserDetailsService {

    private AccountRepository accountRepository;

    @Autowired
    ApiUserDetailService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByEmailOrUsername(username);

        if (!account.isPresent()) {
            throw new UsernameNotFoundException(String.format("Account not found for email %s", username));
        }

        return account.get();
    }
}
