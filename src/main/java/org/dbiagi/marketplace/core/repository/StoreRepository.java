package org.dbiagi.marketplace.core.repository;

import org.dbiagi.marketplace.core.entity.Store;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StoreRepository extends PagingAndSortingRepository<Store, Long> {
    List<Store> findAllByNameNotNullOrderByName();
}
