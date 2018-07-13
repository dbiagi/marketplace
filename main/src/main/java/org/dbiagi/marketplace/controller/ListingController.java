package org.dbiagi.marketplace.controller;

import org.dbiagi.marketplace.dto.ListingDTO;
import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.entity.User;
import org.dbiagi.marketplace.entity.classification.Category;
import org.dbiagi.marketplace.exception.EntityValidationException;
import org.dbiagi.marketplace.exception.ResourceNotFoundException;
import org.dbiagi.marketplace.service.ListingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/listings")
public class ListingController {

    private ListingService listingService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping("/featured")
    public List<Listing> list(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size) {
        return listingService.findFeatured(page, size);
    }

    @GetMapping("/{id}")
    public Listing get(@PathVariable long id) throws ResourceNotFoundException {
        return listingService.find(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('STORE_ATTENDANT')")
    public ListingDTO post(@RequestBody ListingDTO listing, UsernamePasswordAuthenticationToken token) throws EntityValidationException {
        User user = (User) token.getPrincipal();

        listing.setStore(user.getStore());

        return listingService.save(listing);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('STORE_ATTENDANT')")
    public void put(@PathVariable("id") Long id, @RequestBody ListingDTO listing)
        throws ResourceNotFoundException, EntityValidationException {
        listingService.update(id, listing);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('STORE_OWNER')")
    public void delete(@PathVariable Long id) throws ResourceNotFoundException {
        listingService.delete(id);
    }

    @GetMapping("/categories")
    public Page<Category> getCategories(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size) {
        return listingService.getCategories(page, size);
    }

    @PostMapping("/categories")
    public Category postCategory(@RequestBody Category category) throws EntityValidationException {
        return listingService.save(category);
    }
}
