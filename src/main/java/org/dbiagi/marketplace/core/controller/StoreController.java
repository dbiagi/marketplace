package org.dbiagi.marketplace.core.controller;

import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.entity.User;
import org.dbiagi.marketplace.core.exception.ResourceNotFoundException;
import org.dbiagi.marketplace.core.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController extends BaseController{

    private StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public List<Store> list() {
        return storeService.findAll();
    }

    @GetMapping("/{id}")
    public Store get(@PathVariable Long id) throws ResourceNotFoundException {
        Store store = storeService.find(id);

        if (store == null) {
            throw new ResourceNotFoundException(id);
        }

        return store;
    }

    @PutMapping("/{id}")
    public void put(@PathVariable Long id, @RequestBody HashMap<String, Object> store) {
        storeService.update(id, store);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody @Validated(Store.RegistrationGroup.class) Store store) {
        storeService.save(store);

        return new ResponseEntity<>(store, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/users")
    public List<User> getUsers(@PathVariable Long id) throws ResourceNotFoundException {
        Store store = storeService.find(id);

        if (store == null) {
            throw new ResourceNotFoundException(id);
        }

        return store.getUsers();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        storeService.delete(id);
    }
}
