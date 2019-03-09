package org.dbiagi.marketplace.service;

import com.github.slugify.Slugify;
import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.repository.ListingRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListingServiceTest {
    private Listing getListing() {
        Listing listing = new Listing();
        listing.setTitle("Listing X");

        return listing;
    }

    @Test
    void given_EmptySlug_Should_ReturnValidSlug_When_Preparing() {
        Listing listing = getListing();

        ListingRepository listingRepository = mock(ListingRepository.class);
        when(listingRepository.findOneBySlugEquals(""))
            .thenReturn(Optional.empty());

        ListingService listingService = new ListingService(new Slugify(), listingRepository);

        listingService.prepare(listing);

        assertFalse(listing.getSlug().isEmpty());
    }

    @Test
    void given_ExistingListing_Should_HashSlug_When_Preparing() {
        Listing listing = getListing();
        String slug = "listing-slug";
        listing.setSlug(slug);

        ListingRepository listingRepository = mock(ListingRepository.class);
        when(listingRepository.findOneBySlugEquals(slug))
            .thenReturn(Optional.of(listing));

        ListingService listingService = new ListingService(new Slugify(), listingRepository);

        listingService.prepare(listing);

        assertTrue(listing.getSlug().matches(".*[0-9]+$"));
    }
}
