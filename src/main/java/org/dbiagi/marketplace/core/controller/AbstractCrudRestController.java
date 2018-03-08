package org.dbiagi.marketplace.core.controller;

import org.dbiagi.marketplace.core.entity.BaseEntity;
import org.dbiagi.marketplace.core.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;


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
