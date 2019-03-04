package org.dbiagi.marketplace.service;

import com.github.slugify.Slugify;
import org.dbiagi.marketplace.entity.Account;
import org.dbiagi.marketplace.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private Slugify slugify;

    @Autowired
    AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, Slugify slugify) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.slugify = slugify;
    }

    private void slugifyFields(Account account) {
        account.setUsername(slugify.slugify(account.getUsername()));
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
        slugifyFields(account);
    }
}
