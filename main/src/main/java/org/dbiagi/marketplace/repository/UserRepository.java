package org.dbiagi.marketplace.repository;

import org.dbiagi.marketplace.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.store WHERE u.username = :emailOrUsername OR u.email = :emailOrUsername")
    User findByEmailOrUsername(@Param("emailOrUsername") String emailOrUsername);
}
