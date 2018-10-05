package org.dbiagi.marketplace.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dbiagi.marketplace.entity.Store;
import org.dbiagi.marketplace.entity.User;
import org.dbiagi.marketplace.exception.EntityValidationException;
import org.dbiagi.marketplace.exception.ResourceNotFoundException;
import org.dbiagi.marketplace.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.domain.PageRequest.of;

@RestController
@RequestMapping("/api/v1/stores")
@Api(value = "stores", description = "stores endpoint")
public class StoreController extends BaseController {

    private StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    @ApiOperation(value = "Show stores", response = List.class)
    public List<Store> list(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {
        return storeService.findAll(of(page, size));
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
    public void put(@PathVariable Long id, @RequestBody Store store)
        throws ResourceNotFoundException, EntityValidationException {
        storeService.update(id, store);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Store post(@RequestBody Store store) throws EntityValidationException {
        return storeService.save(store, Store.RegistrationGroup.class);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('STORE_OWNER')")
    public void delete(@PathVariable Long id) throws ResourceNotFoundException {
        storeService.delete(id);
    }
}
