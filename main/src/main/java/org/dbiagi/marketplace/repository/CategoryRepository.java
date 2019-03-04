package org.dbiagi.marketplace.repository;

import org.dbiagi.marketplace.entity.classification.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
    Optional<Category> findOneBySlugEquals(String slug);
}
