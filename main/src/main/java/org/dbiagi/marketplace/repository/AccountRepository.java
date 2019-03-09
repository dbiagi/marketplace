package org.dbiagi.marketplace.repository;

import org.dbiagi.marketplace.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
    @Query("SELECT u FROM Account u WHERE u.username = :emailOrUsername OR u.email = :emailOrUsername")
    Optional<Account> findByEmailOrUsername(@Param("emailOrUsername") String emailOrUsername);
}
