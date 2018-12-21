package org.dbiagi.marketplace.service;

import com.github.slugify.Slugify;
import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ListingService {
    private Slugify slugify;
    private ListingRepository listingRepository;

    @Autowired
    public ListingService(Slugify slugify, ListingRepository listingRepository) {
        this.slugify = slugify;
        this.listingRepository = listingRepository;
    }

    private void createSlug(Listing listing) {
        String slug = slugify.slugify(listing.getSlug() != null ? listing.getSlug() : listing.getTitle());

        Optional<Listing> optional = listingRepository.findOneBySlugEquals(slug);

        if (optional.isPresent()) {
            slug = String.format("%s-%d", slug, System.currentTimeMillis());
        }

        listing.setSlug(slug);
    }

    public void prepare(Listing listing) {
        createSlug(listing);
    }
}
