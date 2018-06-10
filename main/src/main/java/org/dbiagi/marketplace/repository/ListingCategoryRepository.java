package org.dbiagi.marketplace.repository;

import org.dbiagi.marketplace.entity.classification.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ListingCategoryRepository extends PagingAndSortingRepository<Category, Long> {
}
