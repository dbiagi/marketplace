package org.dbiagi.marketplace.core.controller;

import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {
    @Autowired
    private StoreRepository storeRepository;

    @GetMapping(name = "stores_root", value = "/")
    public List<Store> all() {
        return storeRepository.findAllByNameNotNullOrderByName();
    }

    @GetMapping(value = "/{id}")
    public Store getById(@PathVariable Long id) {
        return storeRepository.findOne(id);
    }
}
