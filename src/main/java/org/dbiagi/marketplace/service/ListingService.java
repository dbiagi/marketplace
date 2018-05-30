package org.dbiagi.marketplace.service;

import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class ListingService {
    private ListingRepository repository;

    @Autowired
    public ListingService(ListingRepository repository) {
        this.repository = repository;
    }

    public Listing save(Listing listing) {
        return repository.save(listing);
    }

    public Page<Listing> findAll(int page, int size) {
        return repository.findAll(new PageRequest(page, size));
    }

    public Listing find(long id) {
        return repository.findOne(id);
    }
}
