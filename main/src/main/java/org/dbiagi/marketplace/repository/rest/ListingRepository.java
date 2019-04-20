package org.dbiagi.marketplace.repository.rest;

import io.swagger.annotations.Api;
import org.dbiagi.marketplace.entity.Listing;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

@Api
@RepositoryRestResource
public interface ListingRepository extends PagingAndSortingRepository<Listing, Long> {

    @RestResource(path = "featured", rel = "featured")
    List<Listing> findAllByFeaturedTrue(Pageable page);

    Optional<Listing> findOneBySlugEquals(String slug);

    @Secured("ROLE_USER")
    @Override
    <S extends Listing> S save(S entity);

    @PreAuthorize("@listingService.canDelete(authentication, #entity)")
    @Secured("ROLE_USER")
    @Override
    void delete(@P("entity") Listing entity);
}
