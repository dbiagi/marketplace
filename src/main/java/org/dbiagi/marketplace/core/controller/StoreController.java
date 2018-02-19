package org.dbiagi.marketplace.core.controller;

import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.exception.ResourceNotFoundException;
import org.dbiagi.marketplace.core.response.Resource;
import org.dbiagi.marketplace.core.response.ResourceNotFound;
import org.dbiagi.marketplace.core.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Resource<List<Store>> all() {
        return new Resource<>(storeService.findAll());
    }

    @GetMapping("/{id}")
    public Resource<Store> getById(@PathVariable Long id) throws ResourceNotFoundException {
        Store store = storeService.find(id);

        if (store == null) {
            throw new ResourceNotFoundException(id);
        }

        return new Resource<>(store);
    }

    @GetMapping("/{id}/users")
    public ResponseEntity getStoreUsers(@PathVariable Long id) {
        Store store = storeService.find(id);

        return store != null ?
                new ResponseEntity<>(store.getUsers(), HttpStatus.OK) :
                new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody HashMap<String, Object> store) {
        storeService.update(id, store);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResourceNotFound handleStoreNotFound(ResourceNotFoundException ex) {
        ResourceNotFound resourceNotFound = new ResourceNotFound();
        resourceNotFound.setMessage(ex.getMessage());

        return resourceNotFound;
    }
}
