package org.dbiagi.marketplace.service;

import com.github.slugify.Slugify;
import org.dbiagi.marketplace.entity.Account;
import org.dbiagi.marketplace.exception.EntityValidationException;
import org.dbiagi.marketplace.exception.EntityValidationExceptionFactory;
import org.dbiagi.marketplace.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Set;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private Validator validator;
    private Slugify slugify;

    @Autowired
    AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, Validator validator, Slugify slugify) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
        this.slugify = slugify;
    }

    private void validate(Account account, Class<?> group) throws EntityValidationException {
        Set<ConstraintViolation<Account>> violations = group == null ?
            validator.validate(account) :
            validator.validate(account, group);

        if (!violations.isEmpty()) {
            throw new EntityValidationExceptionFactory<Account>().create(violations);
        }
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

    public Account save(Account account) throws EntityValidationException {
        prepare(account);

        validate(account, null);

        return accountRepository.save(account);
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
