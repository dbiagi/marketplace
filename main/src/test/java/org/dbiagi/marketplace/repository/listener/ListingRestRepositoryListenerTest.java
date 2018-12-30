package org.dbiagi.marketplace.repository.listener;

import org.dbiagi.marketplace.BaseSpringRunner;
import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.service.ListingService;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertFalse;

@Tag("Integration")
public class ListingRestRepositoryListenerTest extends BaseSpringRunner {

    @Autowired
    private ListingService listingService;

    @Test
    public void testBeforeCreate() {
        ListingRestRepositoryListener listener = new ListingRestRepositoryListener(listingService);

        Listing listing = new Listing();
        listing.setTitle("some title");

        listener.onBeforeCreate(listing);

        assertFalse("Slug should not be empty", listing.getSlug().isEmpty());
    }

    @Test
    public void testBeforeSave() {
        ListingRestRepositoryListener listener = new ListingRestRepositoryListener(listingService);

        Listing listing = new Listing();
        listing.setTitle("some title");

        listener.onBeforeSave(listing);

        assertFalse("Slug should not be empty", listing.getSlug().isEmpty());
    }
}
