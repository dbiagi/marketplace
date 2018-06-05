package org.dbiagi.marketplace.controller;

import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.entity.ListingCategory;
import org.dbiagi.marketplace.exception.EntityValidationException;
import org.dbiagi.marketplace.exception.ResourceNotFoundException;
import org.dbiagi.marketplace.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/listings")
public class ListingController {

    private ListingService listingService;

    @Autowired
    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping
    public Page<Listing> list(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size) {
        return listingService.findAll(page, size);
    }

    @GetMapping("/{id}")
    public Listing get(@PathVariable long id) {
        return listingService.find(id);
    }

    @GetMapping("/categories")
    public Page<ListingCategory> getCategories(@RequestParam(value = "page", defaultValue = "1") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return listingService.getCategories(page, size);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('STORE_ATTENDANT')")
    public Listing post(@RequestBody Listing listing) throws EntityValidationException {
        return listingService.save(listing);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('STORE_ATTENDANT')")
    public void put(@PathVariable("id") Long id, @RequestBody HashMap<String, Object> fields)
            throws ResourceNotFoundException, EntityValidationException {
        listingService.update(id, fields);
    }
}