package org.dbiagi.marketplace.service;

import com.github.slugify.Slugify;
import org.dbiagi.marketplace.entity.Account;
import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.repository.rest.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
        String slug = slugify.slugify(listing.getSlug() == null || listing.getSlug().isEmpty() ? listing.getTitle() : listing.getSlug());

        Optional<Listing> optional = listingRepository.findOneBySlugEquals(slug);

        if (optional.isPresent()) {
            slug = String.format("%s-%d", slug, System.currentTimeMillis());
        }

        listing.setSlug(slug);
    }

    public void prepare(Listing listing) {
        createSlug(listing);
    }

    public boolean canDelete(Authentication authentication, Listing listing) {
        var principal = authentication.getPrincipal();

        if (!(principal instanceof Account)) {
            return false;
        }

        if (listing == null || listing.getOwner() == null) {
            return false;
        }

        return ((Account) principal).getId().equals(listing.getOwner().getId());

    }
}
