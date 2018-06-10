package org.dbiagi.marketplace.repository;

import org.dbiagi.marketplace.entity.classification.Context;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface ContextRepository extends CrudRepository<Context, Long> {
}
