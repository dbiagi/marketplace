package org.dbiagi.marketplace.core.controller;

import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StoreController {
    @Autowired
    private StoreRepository storeRepository;

    public List<Store> all() {
        return storeRepository.findAllByNameNotNullOrderByName();
    }
}
