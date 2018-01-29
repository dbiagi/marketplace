package org.dbiagi.marketplace.core.controller;

import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.entity.User;
import org.dbiagi.marketplace.core.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private StoreRepository storeRepository;

    @Autowired
    StoreController(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @GetMapping(name = "stores_root", value = "/")
    public List<Store> all() {
        return storeRepository.findAllByNameNotNullOrderByName();
    }

    @GetMapping("/{id}")
    public Store getById(@PathVariable Long id) {
        return storeRepository.findOne(id);
    }

    @GetMapping("/{id}/users")
    public List<User> getStoreUsers(@PathVariable Long id) {
        Store store = storeRepository.findOne(id);

        if (store == null)
            return null;

        return store.getUsers();
    }
}
