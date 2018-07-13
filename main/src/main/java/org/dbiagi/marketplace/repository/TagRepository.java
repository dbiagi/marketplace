package org.dbiagi.marketplace.repository;

import org.dbiagi.marketplace.entity.classification.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {
    Collection<Tag> findByIdIn(List<Long> ids);
}
