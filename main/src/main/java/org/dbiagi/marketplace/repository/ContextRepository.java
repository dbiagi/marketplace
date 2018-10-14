package org.dbiagi.marketplace.repository;

import org.dbiagi.marketplace.entity.classification.Context;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ContextRepository extends PagingAndSortingRepository<Context, Long> {
}
