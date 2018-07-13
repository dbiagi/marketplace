package org.dbiagi.marketplace.repository;

import org.dbiagi.marketplace.entity.classification.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
    Collection<Category> findByIdIn(List<Long> ids);
}
