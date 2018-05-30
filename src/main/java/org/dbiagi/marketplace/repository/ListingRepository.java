package org.dbiagi.marketplace.repository;

import org.dbiagi.marketplace.entity.Listing;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

@Component
public interface ListingRepository extends PagingAndSortingRepository<Listing, Long> {
}
