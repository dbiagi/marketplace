package org.dbiagi.marketplace.core.repository;

import org.dbiagi.marketplace.core.entity.Store;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StoreRepository extends CrudRepository<Store, Long> {
    List<Store> findAllByNameNotNullOrderByName();
}
