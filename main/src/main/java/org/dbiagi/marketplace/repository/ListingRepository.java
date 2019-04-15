package org.dbiagi.marketplace.repository;

import io.swagger.annotations.Api;
import org.dbiagi.marketplace.entity.Listing;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@Api
@RepositoryRestResource
public interface ListingRepository extends PagingAndSortingRepository<Listing, Long> {
    @Query("SELECT l FROM Listing l " +
        "LEFT JOIN FETCH l.categories AS c " +
        "LEFT JOIN FETCH l.tags AS t " +
        "LEFT JOIN FETCH c.children " +
        "WHERE l.featured = true")
    @RestResource(path = "featured", rel = "featured")
    List<Listing> findAllFeatured(Pageable page);

    Optional<Listing> findOneBySlugEquals(String slug);
}
