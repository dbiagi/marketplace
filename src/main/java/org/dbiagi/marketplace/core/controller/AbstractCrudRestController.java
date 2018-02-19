package org.dbiagi.marketplace.core.controller;

import org.dbiagi.marketplace.core.entity.BaseEntity;
import org.dbiagi.marketplace.core.service.EntityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class AbstractCrudRestController<T extends BaseEntity> {

    private final EntityService entityService;

    public AbstractCrudRestController(EntityService entityService) {
        this.entityService = entityService;
    }

    @GetMapping("/{id}")
    public BaseEntity get(@PathVariable Long id) {
        return entityService.find(id);
    }

}
