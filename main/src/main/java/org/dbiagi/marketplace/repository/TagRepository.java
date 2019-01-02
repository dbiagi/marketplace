package org.dbiagi.marketplace.repository;

import org.dbiagi.marketplace.entity.classification.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {
    Optional<Tag> findOneBySlugEquals(String slug);
}
