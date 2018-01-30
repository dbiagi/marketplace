package org.dbiagi.marketplace.core.service;

import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    private StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public Store register(Store store) {
        storeRepository.save(store);

        return store;
    }

    public List<Store> findAll() {
        return storeRepository.findAllByNameNotNullOrderByName();
    }

    public Store find(Long id) {
        return storeRepository.findOne(id);
    }


}
