package org.dbiagi.marketplace.repository;

import org.dbiagi.marketplace.entity.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StoreRepository extends PagingAndSortingRepository<Store, Long> {
    List<Store> findAllByNameNotNullOrderByName(Pageable pageable);
}
