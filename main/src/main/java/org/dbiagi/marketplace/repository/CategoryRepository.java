package org.dbiagi.marketplace.repository;

import org.dbiagi.marketplace.entity.classification.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;

@RepositoryRestResource
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
    Collection<Category> findByIdIn(List<Long> ids);
}
