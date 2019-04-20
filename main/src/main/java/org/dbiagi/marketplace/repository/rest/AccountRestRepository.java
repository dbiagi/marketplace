package org.dbiagi.marketplace.repository.rest;

import io.swagger.annotations.Api;
import org.dbiagi.marketplace.entity.Account;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@Api
@RepositoryRestResource
public interface AccountRestRepository extends PagingAndSortingRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
