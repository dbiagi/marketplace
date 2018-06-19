package org.dbiagi.marketplace.service;

import org.dbiagi.marketplace.entity.BaseEntity;

import java.util.List;
import java.util.Map;

public interface EntityService {
    List<? extends BaseEntity> findAll();

    BaseEntity find(Long id);

    BaseEntity save(BaseEntity entity);

    void delete(BaseEntity entity);

    void delete(Long id);

    BaseEntity update(Long id, Map<String, Object> values);
}
