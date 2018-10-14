package org.dbiagi.marketplace.listener;

import com.github.slugify.Slugify;
import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ListingRepositoryListener extends AbstractRepositoryEventListener<Listing> {

    private Slugify slugify;
    private ListingRepository listingRepository;

    @Autowired
    ListingRepositoryListener(Slugify slugify, ListingRepository listingRepository) {
        this.slugify = slugify;
        this.listingRepository = listingRepository;
    }

    private void createSlug(Listing entity) {
        String slug = slugify.slugify(entity.getSlug() != null ? entity.getSlug() : entity.getTitle());

        Optional<Listing> optional = listingRepository.findOneBySlugEquals(slug);

        if (optional.isPresent()) {
            slug = String.format("%s-%d", slug, System.currentTimeMillis());
        }

        entity.setSlug(slug);
    }

    @Override
    protected void onBeforeCreate(Listing entity) {
        createSlug(entity);
    }

    @Override
    protected void onBeforeSave(Listing entity) {
        super.onBeforeSave(entity);
    }
}
