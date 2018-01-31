package org.dbiagi.marketplace.core.controller;

import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.entity.User;
import org.dbiagi.marketplace.core.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public List<Store> all() {
        return storeService.findAll();
    }

    @GetMapping("/{id}")
    public Store getById(@PathVariable Long id) {
        return storeService.find(id);
    }

    @GetMapping("/{id}/users")
    public List<User> getStoreUsers(@PathVariable Long id) {
        Store store = storeService.find(id);

        List<User> users = store != null ? store.getUsers() : new ArrayList<>();

        return users;
    }

    @PostMapping
    public ResponseEntity register(@RequestBody @Validated Store store) {
        storeService.register(store);

        return new ResponseEntity<>(store, HttpStatus.CREATED);
    }
}
