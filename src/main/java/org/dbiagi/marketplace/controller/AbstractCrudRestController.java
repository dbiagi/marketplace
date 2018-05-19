package org.dbiagi.marketplace.controller;

import org.dbiagi.marketplace.entity.BaseEntity;
import org.dbiagi.marketplace.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

/**
 * I created this to generate a generic crud, but I couldn't figure a way to make this work
 * I'll leave this here to try again sometime later.
 */
public abstract class AbstractCrudRestController {

    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public AbstractCrudRestController(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    @GetMapping("/{id}")
    public BaseEntity get(@PathVariable Long id) {
        return getEntityService().find(id);
    }

    @PostMapping
    public void post(@RequestBody BaseEntity entity) {
        getEntityService().save(entity);
    }

    @DeleteMapping
    public void delete() {

    }

    /**
     * Get entity crud service.
     *
     * @return The crud service for the entity
     */
    abstract EntityService getEntityService();
}
