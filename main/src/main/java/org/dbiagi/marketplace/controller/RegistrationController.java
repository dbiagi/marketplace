package org.dbiagi.marketplace.controller;

import org.dbiagi.marketplace.entity.Account;
import org.dbiagi.marketplace.entity.dto.AccountRegistration;
import org.dbiagi.marketplace.exception.EntityConstraintValidationException;
import org.dbiagi.marketplace.mailer.RegistrationMailer;
import org.dbiagi.marketplace.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    public static final String REGISTRATION_URI = "/register";
    private AccountService accountService;
    private RegistrationMailer registrationMailer;

    @Autowired
    RegistrationController(AccountService accountService, RegistrationMailer registrationMailer) {
        this.accountService = accountService;
        this.registrationMailer = registrationMailer;
    }

    @PostMapping(REGISTRATION_URI)
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody AccountRegistration accountRegistration) throws EntityConstraintValidationException {
        Account account = accountService.register(accountRegistration);

        registrationMailer.send(account);
    }
}
