package org.dbiagi.marketplace.core.service;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class FakerFactory {

    @Bean
    public Faker getFaker() {
        return new Faker(new Locale("pt-BR"));
    }
}
