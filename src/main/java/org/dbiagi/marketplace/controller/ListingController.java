package org.dbiagi.marketplace.controller;

import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
}
