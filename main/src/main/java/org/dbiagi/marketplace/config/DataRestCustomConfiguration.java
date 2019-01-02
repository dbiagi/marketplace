package org.dbiagi.marketplace.config;

import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.entity.classification.Category;
import org.dbiagi.marketplace.entity.classification.Tag;
import org.dbiagi.marketplace.repository.CategoryRepository;
import org.dbiagi.marketplace.repository.ListingRepository;
import org.dbiagi.marketplace.repository.TagRepository;
import org.dbiagi.marketplace.repository.validation.DefaultEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;

@Component
public class DataRestCustomConfiguration extends RepositoryRestConfigurerAdapter {
    private DefaultEntityValidator defaultEntityValidator;

    @Autowired
    public DataRestCustomConfiguration(DefaultEntityValidator defaultEntityValidator) {
        this.defaultEntityValidator = defaultEntityValidator;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.withEntityLookup()
            .forRepository(ListingRepository.class, Listing::getSlug, ListingRepository::findOneBySlugEquals)
            .forRepository(CategoryRepository.class, Category::getSlug, CategoryRepository::findOneBySlugEquals)
            .forRepository(TagRepository.class, Tag::getSlug, TagRepository::findOneBySlugEquals)
        ;

        config.setReturnBodyOnCreate(true);
    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        validatingListener
            .addValidator("beforeCreate", defaultEntityValidator);
    }
}
