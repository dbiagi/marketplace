package org.dbiagi.marketplace.service;

import org.dbiagi.marketplace.entity.Account;
import org.dbiagi.marketplace.normalizer.Username;
import org.dbiagi.marketplace.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private Username usernameNormalizer;

    @Autowired
    AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, Username usernameNormalizer) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.usernameNormalizer = usernameNormalizer;
    }

    private void normalizeUsername(Account account) {
        account.setUsername(usernameNormalizer.normalize(account.getUsername()));
    }

    private void encryptPassword(Account account) {
        if (account.getPlainPassword() != null) {
            account.setPassword(passwordEncoder.encode(account.getPlainPassword()));
            account.setPlainPassword(null);
        }
    }

    public void save(Collection<Account> accounts) {
        accounts.forEach(this::prepare);

        accountRepository.saveAll(accounts);
    }

    public Account findByEmailOrUsername(String emailOrUsername) {
        return accountRepository.findByEmailOrUsername(emailOrUsername);
    }

    public void prepare(Account account) {
        encryptPassword(account);
        normalizeUsername(account);
    }
}