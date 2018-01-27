package org.dbiagi.marketplace.core.repository;

import org.dbiagi.marketplace.core.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :emailOrUsername OR u.email = :emailOrUsername")
    User findByEmailOrUsername(@Param("emailOrUsername") String emailOrUsername);
}
