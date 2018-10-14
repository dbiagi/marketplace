package org.dbiagi.marketplace.config;

import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class ServicesConfiguration {

    @Bean
    public Faker getFaker() {
        return new Faker(new Locale("pt-BR"));
    }

    @Bean
    public Slugify getSlugify() {
        return new Slugify().withLowerCase(true);
    }
}
