package org.dbiagi.marketplace.core.service;

import org.dbiagi.marketplace.core.entity.BaseEntity;

import java.util.List;
import java.util.Map;

public interface EntityService {
    public List<? extends BaseEntity> findAll();

    public BaseEntity find(Long id);

    public BaseEntity save(BaseEntity entity);

    public void delete(BaseEntity entity);

    public void delete(Long id);

    public BaseEntity update(Long id, Map<String, Object> values);
}
