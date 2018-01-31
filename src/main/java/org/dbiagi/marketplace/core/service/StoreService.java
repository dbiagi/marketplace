package org.dbiagi.marketplace.core.service;

import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    private StoreRepository storeRepository;

    private UserService userService;

    @Autowired
    public StoreService(StoreRepository storeRepository, UserService userService) {
        this.storeRepository = storeRepository;
        this.userService = userService;
    }

    public Store register(Store store) {
        storeRepository.save(store);

        store.getUsers().forEach(userService::save);

        return store;
    }

    public List<Store> findAll() {
        return storeRepository.findAllByNameNotNullOrderByName();
    }

    public Store find(Long id) {
        return storeRepository.findOne(id);
    }


}
