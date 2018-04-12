package org.dbiagi.marketplace.core.controller;

import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.entity.User;
import org.dbiagi.marketplace.core.exception.EntityValidationException;
import org.dbiagi.marketplace.core.exception.EntityValidationExceptionFactory;
import org.dbiagi.marketplace.core.exception.ResourceNotFoundException;
import org.dbiagi.marketplace.core.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/stores")
public class StoreController extends BaseController {

    private StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('STORE_ATTENDANT')")
    public List<Store> list() {
        return storeService.findAll();
    }

    @GetMapping("/{id}/users")
    @PreAuthorize("hasAuthority('STORE_ATTENDANT')")
    public List<User> listUsers(@PathVariable Long id) throws ResourceNotFoundException {
        Store store = storeService.find(id);

        return store.getUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('STORE_ATTENDANT')")
    public Store get(@PathVariable Long id) throws ResourceNotFoundException {
        return storeService.find(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('STORE_OWNER')")
    public void put(@PathVariable Long id, @RequestBody HashMap<String, Object> fields)
            throws ResourceNotFoundException {

        storeService.update(id, fields);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Store post(@RequestBody Store store) throws EntityValidationException {
        Set<ConstraintViolation<Store>> violations = validator.validate(store, Store.RegistrationGroup.class);

        if (!violations.isEmpty()) {
            throw new EntityValidationExceptionFactory<Store>().create(violations);
        }

        return storeService.save(store);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('STORE_OWNER')")
    public void delete(@PathVariable Long id) throws ResourceNotFoundException {
        Store store = storeService.find(id);

        if (store == null) {
            throw new ResourceNotFoundException(id);
        }

        storeService.delete(id);
    }
}
