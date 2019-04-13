package org.dbiagi.marketplace.service;

import org.dbiagi.marketplace.entity.Account;
import org.dbiagi.marketplace.entity.dto.AccountRegistration;
import org.dbiagi.marketplace.exception.EntityConstraintValidationException;
import org.dbiagi.marketplace.normalizer.UsernameNormalizer;
import org.dbiagi.marketplace.repository.AccountRepository;
import org.dbiagi.marketplace.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private UsernameNormalizer usernameNormalizer;
    private Validator validator;

    @Autowired
    AccountService(AccountRepository accountRepository,
                   PasswordEncoder passwordEncoder,
                   UsernameNormalizer usernameNormalizer,
                   Validator validator) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.usernameNormalizer = usernameNormalizer;
        this.validator = validator;
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

    public Account save(Account account) throws EntityConstraintValidationException {
        prepare(account);

        validate(account);

        return accountRepository.save(account);
    }

    public void validate(Account account) throws EntityConstraintValidationException {
        var violations = validator.validate(account);

        if (!violations.isEmpty()) {
            throw new EntityConstraintValidationException(
                violations.stream()
                    .map(v -> new ValidationError("", v.getPropertyPath().toString(), v.getMessage()))
                    .collect(Collectors.toList())
            );
        }
    }

    public Iterable<Account> save(Collection<Account> accounts) {
        accounts.forEach(this::prepare);

        return accountRepository.saveAll(accounts);
    }

    public Optional<Account> findByEmailOrUsername(String emailOrUsername) {
        return accountRepository.findByEmailOrUsername(emailOrUsername);
    }

    public void prepare(Account account) {
        encryptPassword(account);
        normalizeUsername(account);
    }

    public Account register(AccountRegistration accountRegistration) throws EntityConstraintValidationException {
        var account = new Account();
        account.setEmail(accountRegistration.email);
        account.setName(accountRegistration.name);
        account.setPlainPassword(accountRegistration.plainPassword);
        account.setUsername(accountRegistration.username);

        return save(account);
    }
}
