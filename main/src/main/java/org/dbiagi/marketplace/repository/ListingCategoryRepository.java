package org.dbiagi.marketplace.repository;

import org.dbiagi.marketplace.entity.ListingCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ListingCategoryRepository extends PagingAndSortingRepository<ListingCategory, Long> {
}
