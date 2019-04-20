package org.dbiagi.marketplace.repository;

import org.dbiagi.marketplace.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT u FROM Account u WHERE u.username = :emailOrUsername OR u.email = :emailOrUsername")
    Optional<Account> findByEmailOrUsername(@Param("emailOrUsername") String emailOrUsername);
}
