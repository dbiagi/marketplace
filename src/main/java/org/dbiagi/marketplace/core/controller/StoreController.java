package org.dbiagi.marketplace.core.controller;

import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.exception.ResourceNotFoundException;
import org.dbiagi.marketplace.core.response.EntityResponse;
import org.dbiagi.marketplace.core.response.ResourceNotFound;
import org.dbiagi.marketplace.core.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/stores")
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
    public EntityResponse<Store> getById(@PathVariable Long id) throws ResourceNotFound {
        Store store = storeService.find(id);

        if (store == null) {
            throw new ResourceNotFound();
        }

        return new EntityResponse<>(store);
    }

    @GetMapping("/{id}/users")
    public EntityResponse getStoreUsers(@PathVariable Long id) {
        Store store = storeService.find(id);

        return store != null ?
                new EntityResponse<>(store.getUsers()) :
                new EntityResponse<>(new ArrayList<>());
    }

    @PutMapping("/{id}")
    public Store update(@PathVariable Long id, @RequestBody @Validated HashMap<String, Object> store) {
        return storeService.update(id, store);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResourceNotFound handleStoreNotFound(ResourceNotFoundException ex) {
        return new ResourceNotFound();
    }
}
