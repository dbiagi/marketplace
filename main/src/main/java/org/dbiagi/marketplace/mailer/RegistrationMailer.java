package org.dbiagi.marketplace.mailer;

import org.dbiagi.marketplace.entity.Account;
import org.springframework.stereotype.Service;

@Service
public class RegistrationMailer implements Mailer {

    public void send(Account account) {
        send();
    }

    @Override
    public void send(){}
}
