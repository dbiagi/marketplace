package org.dbiagi.marketplace.repository;

import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.entity.classification.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ListingRepository extends PagingAndSortingRepository<Listing, Long> {
    @Query("SELECT l FROM Listing l " +
        "LEFT JOIN FETCH l.categories AS c " +
        "LEFT JOIN FETCH l.tags AS t " +
        "LEFT JOIN FETCH c.children " +
        "WHERE l.featured = true")
    List<Listing> findAllFeatured(Pageable page);

    @Query("SELECT c FROM Listing l LEFT JOIN l.categories c WHERE l.id = :listingId")
    List<Category> findListingCategories(@Param("listingId") Long listingId);

    @Query("SELECT l FROM Listing l " +
        "LEFT JOIN FETCH l.categories AS c " +
        "LEFT JOIN FETCH l.tags AS t " +
        "LEFT JOIN FETCH c.children " +
        "WHERE l.id = :id")
    Listing find(@Param("id") Long id);
}
