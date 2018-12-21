package org.dbiagi.marketplace.repository.listener;

import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

@Component
public class ListingRestRepositoryListener extends AbstractRepositoryEventListener<Listing> {

    private ListingService listingService;

    @Autowired
    ListingRestRepositoryListener(ListingService listingService) {
        this.listingService = listingService;
    }

    @Override
    protected void onBeforeCreate(Listing entity) {
        listingService.prepare(entity);
    }

    @Override
    protected void onBeforeSave(Listing entity) {
        listingService.prepare(entity);
    }
}
