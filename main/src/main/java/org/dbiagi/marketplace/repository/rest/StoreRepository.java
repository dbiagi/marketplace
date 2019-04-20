package org.dbiagi.marketplace.repository.rest;

import org.dbiagi.marketplace.entity.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface StoreRepository extends PagingAndSortingRepository<Store, Long> {
    List<Store> findAllByNameNotNullOrderByName(Pageable pageable);
}
