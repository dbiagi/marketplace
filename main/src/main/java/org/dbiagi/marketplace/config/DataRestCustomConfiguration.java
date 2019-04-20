package org.dbiagi.marketplace.config;

import org.dbiagi.marketplace.entity.Account;
import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.entity.classification.Category;
import org.dbiagi.marketplace.entity.classification.Tag;
import org.dbiagi.marketplace.repository.rest.AccountRestRepository;
import org.dbiagi.marketplace.repository.rest.CategoryRestRepository;
import org.dbiagi.marketplace.repository.rest.ListingRepository;
import org.dbiagi.marketplace.repository.rest.TagRepository;
import org.dbiagi.marketplace.validation.DefaultEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

@Component
public class DataRestCustomConfiguration implements RepositoryRestConfigurer {
    private DefaultEntityValidator defaultEntityValidator;

    @Autowired
    public DataRestCustomConfiguration(DefaultEntityValidator defaultEntityValidator) {
        this.defaultEntityValidator = defaultEntityValidator;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.withEntityLookup()
            .forRepository(ListingRepository.class, Listing::getSlug, ListingRepository::findOneBySlugEquals)
            .forRepository(CategoryRestRepository.class, Category::getSlug, CategoryRestRepository::findOneBySlugEquals)
            .forRepository(TagRepository.class, Tag::getSlug, TagRepository::findOneBySlugEquals)
            .forRepository(AccountRestRepository.class, Account::getUsername, AccountRestRepository::findByUsername)
        ;

        config.setReturnBodyOnCreate(true);
    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        validatingListener
            .addValidator("beforeCreate", defaultEntityValidator);
    }
}
